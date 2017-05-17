package com.capgemini.customeronboarding;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.distributed.jgroups.JGroupsConnector;
import org.axonframework.commandhandling.distributed.jgroups.JGroupsConnectorFactoryBean;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.domain.EventMessage;
import org.axonframework.eventhandling.ClassNamePrefixClusterSelector;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.ClusterSelector;
import org.axonframework.eventhandling.ClusteringEventBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventBusTerminal;
import org.axonframework.eventhandling.SimpleCluster;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventhandling.replay.DiscardingIncomingMessageHandler;
import org.axonframework.eventhandling.replay.IncomingMessageHandler;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.management.EventStoreManagement;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.serializer.xml.XStreamSerializer;
import org.axonframework.unitofwork.NoTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.capgemini.customeronboarding.addcustomer.constants.ApplicationPropertyConstants;
import com.capgemini.customeronboarding.aggregate.Customer;
import com.capgemini.customeronboarding.eventhandler.addcustomer.CustomerAddedEventHandler;
import com.mongodb.MongoClient;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories
@EnableJms
@ComponentScan(basePackages = { "com.capgemini.customeronboarding" })
public class CreatecustomerApplication {

	@Autowired
	private Environment envirnment;
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	public static void main(String[] args) {
		SpringApplication.run(CreatecustomerApplication.class, args);
		// let's start with the Command Bus
	}

	@Autowired
	private MongoDbFactory mongoDbFactory;

	@Bean
	public MongoTemplate mongoTemplate(MongoMappingContext context) {

		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

		return mongoTemplate;

	}

	@Bean
	public org.axonframework.eventstore.mongo.MongoTemplate eventStoreMongoTemplate() throws UnknownHostException {
		String mongoHost = envirnment.getProperty(ApplicationPropertyConstants.AXON_MONGO_DB_HOST,
				ApplicationPropertyConstants.AXON_MONGO_DB_HOST_DEFAULT);
		int mongoPort = (null == envirnment.getProperty(ApplicationPropertyConstants.AXON_MONGO_DB_PORT))
				? ApplicationPropertyConstants.AXON_MONGO_DB_PORT_DEFAULT
				: Integer.parseInt(envirnment.getProperty(ApplicationPropertyConstants.AXON_MONGO_DB_PORT));

		String dbName = envirnment.getProperty(ApplicationPropertyConstants.AXON_MONGO_DB_DATABASE,
				ApplicationPropertyConstants.AXON_MONGO_DB_DATABASE_DEFAULT);
		String domainEventStoreName = envirnment.getProperty(
				ApplicationPropertyConstants.AXON_MONGO_DB_DOMAIN_EVENT_STORE,
				ApplicationPropertyConstants.AXON_MONGO_DB_DOMAIN_EVENT_STORE_DEFAULT);
		String snapshotEventStoreName = envirnment.getProperty(
				ApplicationPropertyConstants.AXON_MONGO_DB_SNAPSHOT_EVENT_STORE,
				ApplicationPropertyConstants.AXON_MONGO_DB_SNAPSHOT_EVENT_STORE_DEFAULT);
		String dbUserName = envirnment.getProperty(ApplicationPropertyConstants.AXON_MONGO_DB_USERNAME);
		char[] dbPassword = (null != envirnment.getProperty(ApplicationPropertyConstants.AXON_MONGO_DB_PASSWORD))
				? envirnment.getProperty(ApplicationPropertyConstants.AXON_MONGO_DB_PASSWORD).toCharArray() : null;

		MongoClient mongoClient = new MongoClient(mongoHost, mongoPort);
		DefaultMongoTemplate eventStoreMongoTemplate = new DefaultMongoTemplate(mongoClient, dbName,
				domainEventStoreName, snapshotEventStoreName, dbUserName, dbPassword);
		return eventStoreMongoTemplate;
	}

	@Bean
	public EventStore mongoEventStore() throws UnknownHostException {
		MongoEventStore eventStore = new MongoEventStore(eventStoreMongoTemplate());
		return eventStore;
	}

	@Bean
	public EventSourcingRepository<Customer> customerEventRepository() throws UnknownHostException {
		EventSourcingRepository<Customer> repository = new EventSourcingRepository<Customer>(Customer.class,
				mongoEventStore());
		repository.setEventBus(clusteringEventBus());
		return repository;

	}

	@SuppressWarnings("rawtypes")
	@Bean
	public AggregateAnnotationCommandHandler customerCommandHandler() throws Exception {
		return AggregateAnnotationCommandHandler.subscribe(Customer.class, customerEventRepository(), commandBus());
	}

	@Bean
	public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor()
			throws UnknownHostException {
		AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();

		processor.setEventBus(clusteringEventBus());
		return processor;
	}

	@Bean
	public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() throws Exception {
		AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
		processor.setCommandBus(commandBus());
		return processor;
	}

	@Bean
	public CommandBus commandBus() throws Exception {
		JGroupsConnector connector = (JGroupsConnector) jGroupsConnectorFactoryBean().getObject();
		DistributedCommandBus commandBus = new DistributedCommandBus(connector);
		return commandBus;
	}

	@Bean
	public CommandGateway getCommandGateway() throws Exception {
		CommandGateway commandGateway = new DefaultCommandGateway(commandBus());
		return commandGateway;
	}

	/*
	 * @Bean public EventBus eventBus() { EventBus eventBus = new
	 * SimpleEventBus();
	 * AnnotationEventListenerAdapter.subscribe(customerAddedEventHandler(),
	 * eventBus); return eventBus; }
	 */
	@Bean
	public CustomerAddedEventHandler customerAddedEventHandler() {
		return new CustomerAddedEventHandler();
	}

	@Bean
	public JGroupsConnectorFactoryBean jGroupsConnectorFactoryBean() {
		JGroupsConnectorFactoryBean jGroupsConnectorFactoryBean = new JGroupsConnectorFactoryBean();
		// jGroupsConnectorFactoryBean.setChannelName(envirnment.getProperty(ApplicationPropertyConstants.JGROUP_CLUSTER_NAME));
		jGroupsConnectorFactoryBean
				.setClusterName(envirnment.getProperty(ApplicationPropertyConstants.JGROUP_CLUSTER_NAME));
		jGroupsConnectorFactoryBean
				.setConfiguration(envirnment.getProperty(ApplicationPropertyConstants.JGROUP_CONFIGURATION));
		return jGroupsConnectorFactoryBean;
	}

	@Bean
	public XStreamSerializer xStreamSerializer() {
		return new XStreamSerializer();
	}

	/*************** cluster cofiguration *******************/
	/**
	 * A cluster which can be used to "cluster" together event handlers. This
	 * implementation is based on {@link SimpleCluster} and it would be used to
	 * cluster event handlers that would listen to events thrown normally within
	 * the application.
	 *
	 * @return an instance of {@link SimpleCluster}
	 */
	@Bean
	public Cluster normalCluster() {
		SimpleCluster simpleCluster = new SimpleCluster("simpleCluster");
		return simpleCluster;
	}

	/**
	 * A cluster which can be used to "cluster" together event handlers. This
	 * implementation is based on {@link SimpleCluster} and it would be used to
	 * cluster event handlers that would listen to replayed events.
	 *
	 * As can be seen, the bean is just a simple implementation of
	 * {@link SimpleCluster} there is nothing about it that says it would be
	 * able to handle replayed events. The bean definition #replayCluster is
	 * what makes this bean able to handle replayed events.
	 *
	 * @return an instance of {@link SimpleCluster}
	 */
	@Bean
	public Cluster replay() {
		SimpleCluster simpleCluster = new SimpleCluster("replayCluster");
		return simpleCluster;
	}

	/**
	 * Takes the #replay() cluster and wraps it with a Replaying Cluster,
	 * turning the event handlers that are registered to be able to pick up
	 * events when events are replayed.
	 *
	 * @return an instance of {@link ReplayingCluster}
	 * @throws UnknownHostException
	 */
	@Bean
	public ReplayingCluster replayCluster() throws UnknownHostException {
		IncomingMessageHandler incomingMessageHandler = new DiscardingIncomingMessageHandler();
		EventStoreManagement eventStore = (EventStoreManagement) mongoEventStore();
		return new ReplayingCluster(replay(), eventStore, new NoTransactionManager(), 0, incomingMessageHandler);
	}

	/**
	 * This configuration registers event handlers with the two defined clusters
	 *
	 * @return an instance of {@link ClusterSelector}
	 * @throws UnknownHostException
	 */
	@Bean
	public ClusterSelector clusterSelector() throws UnknownHostException {
		Map<String, Cluster> clusterMap = new HashMap<>();
		clusterMap.put("com.capgemini.customeronboarding.eventhandler", normalCluster());
		clusterMap.put("com.capgemini.customeronboarding.activemq", normalCluster());
		clusterMap.put("com.capgemini.customeronboarding.replay", replayCluster());
		return new ClassNamePrefixClusterSelector(clusterMap);
	}

	/**
	 * An {@link EventBusTerminal} which publishes application domain events
	 * onto the normal cluster
	 *
	 * @return an instance of {@link EventBusTerminal}
	 */
	@Bean
	public EventBusTerminal terminal() {
		return new EventBusTerminal() {
			@Override
			public void publish(EventMessage... events) {
				normalCluster().publish(events);
			}

			@Override
			public void onClusterCreated(Cluster cluster) {

			}
		};
	}

	/**
	 * This replaces the simple event bus that was initially used. The
	 * clustering event bus is needed to be able to route events to event
	 * handlers in the clusters. It is configured with a
	 * {@link EventBusTerminal} defined by #terminal(). The EventBusTerminal
	 * contains the configuration rules which determines which cluster gets an
	 * incoming event
	 *
	 * @return a {@link ClusteringEventBus} implementation of {@link EventBus}
	 * @throws UnknownHostException
	 */
	@Bean
	public EventBus clusteringEventBus() throws UnknownHostException {
		ClusteringEventBus clusteringEventBus = new ClusteringEventBus(clusterSelector(), terminal());
		return clusteringEventBus;
	}

	/********************* Active MQ **********************/

	@Bean
	public Queue queue() {
		return new ActiveMQQueue(envirnment.getProperty(ApplicationPropertyConstants.MQ_QUEUE_NAME));
	}

	@Bean
	public DefaultJmsListenerContainerFactory myFactory(DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setMessageConverter(jacksonJmsMessageConverter());
		return factory;
	}


	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

}
