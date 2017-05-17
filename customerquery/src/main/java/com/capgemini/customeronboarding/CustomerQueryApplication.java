package com.capgemini.customeronboarding;

import java.util.Arrays;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
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
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.capgemini.customeronboarding.activemq.consumer.CustomerCreatedMessageConsumer;
import com.capgemini.customeronboarding.addcustomer.constants.ApplicationPropertyConstants;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories
@EnableJms
@ComponentScan(basePackages = { "com.capgemini.customeronboarding" })
public class CustomerQueryApplication {

	@Autowired
	private Environment envirnment;
	
	@Autowired
	private MongoDbFactory mongoDbFactory;

	public static void main(String[] args) {
		SpringApplication.run(CustomerQueryApplication.class, args);
		// let's start with the Command Bus
	}

	@Bean
	public MongoTemplate mongoTemplate(MongoMappingContext context) {

		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

		return mongoTemplate;

	}

	/********************* Active MQ **********************/

	@Bean
	public Queue queue() {
		return new ActiveMQQueue(envirnment.getProperty(ApplicationPropertyConstants.MQ_QUEUE_NAME));
	}

	/*@Bean
	public ConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://DESKTOP-JR55A10:61616");
		
		<bean id="jmsFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
		    <property name="brokerURL">
		      <value>tcp://localhost:61616</value>
		    </property>
		  </bean>
		return activeMQConnectionFactory;
	}*/
	
	@Bean
	public DefaultJmsListenerContainerFactory myFactory(DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory());
		factory.setMessageConverter(jacksonJmsMessageConverter());
		return factory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
		jmsTemplate.setConnectionFactory(connectionFactory());
		return jmsTemplate;
	}


	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
	 
    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
     
//    private static final String ORDER_QUEUE = "order-queue";
//    private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";
     
    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
        connectionFactory.setTrustedPackages(Arrays.asList("com.capgemini.customeronboardings"));
        return connectionFactory;
    }
    /*
     * Optionally you can use cached connection factory if performance is a big concern.
     */
 
    @Bean
    public ConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(connectionFactory());
        connectionFactory.setSessionCacheSize(10);
        return connectionFactory;
    }
     
    /*
     * Message listener container, used for invoking messageReceiver.onMessage on message reception.
     */
    @Autowired
    private CustomerCreatedMessageConsumer customerCreatedMessageConsumer;
    
    @Bean
    public MessageListenerContainer getContainer(){
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName("onboarding.queue");
        container.setMessageListener(customerCreatedMessageConsumer);
        return container;
    }
 
    /*
     * Used for Sending Messages.
     */
   /* @Bean
    public JmsTemplate jmsTemplate(){
    	JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName("onboarding.queue");
        template.setMessageConverter(jacksonJmsMessageConverter());
        return template;
    }
    @Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
     */
  /*   
    @Bean
    MessageConverter converter(){
        return new SimpleMessageConverter();
    }*/
}
