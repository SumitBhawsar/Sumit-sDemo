package com.capgemini.customeronboarding.dao.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.customeronboarding.dao.entity.CustomerEntity;

@Repository("customerRepository")
@Qualifier("customerRepository")
public interface CustomerRepository extends MongoRepository<CustomerEntity, Integer>{
	
	CustomerEntity findByCustomerId(Integer customerId);

}
