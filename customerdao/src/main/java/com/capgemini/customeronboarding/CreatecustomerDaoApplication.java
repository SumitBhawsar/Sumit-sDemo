/*package com.capgemini.customeronboarding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;


@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.capgemini.customeronboarding"})
public class CreatecustomerDaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreatecustomerDaoApplication.class, args);
	}

	@Autowired
	private MongoDbFactory mongoDbFactory;
	
	@Bean
	public MongoTemplate mongoTemplate( MongoMappingContext context) {
		
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

		return mongoTemplate;

	}
	
}
//*/