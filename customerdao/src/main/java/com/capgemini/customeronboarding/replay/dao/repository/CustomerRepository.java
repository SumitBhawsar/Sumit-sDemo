package com.capgemini.customeronboarding.replay.dao.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.customeronboarding.replay.dao.entity.CustomerEntity;


@Repository("replayCustomerRepository")
@Qualifier("replayCustomerRepository")
public interface CustomerRepository extends MongoRepository<CustomerEntity, Integer>{
	
	CustomerEntity findByCustomerId(Integer customerId);

}
