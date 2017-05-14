package com.capgemini.customeronboarding.replay.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.capgemini.customeronboarding.dao.dto.CustomerDto;
import com.capgemini.customeronboarding.replay.dao.converter.CustomerEntityConverter;
import com.capgemini.customeronboarding.replay.dao.entity.CustomerEntity;
import com.capgemini.customeronboarding.replay.dao.repository.CustomerRepository;

@Service("replayCustomerDao")
@Qualifier("replayCustomerDao")
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	@Qualifier("replayCustomerEntityConverter")
	private CustomerEntityConverter customerEntityConverter;

	@Autowired
	@Qualifier("replayCustomerRepository")
	private CustomerRepository customerRepository;

	public void save(CustomerDto customer) {
		CustomerEntity customerEntity = customerEntityConverter.toCustomerEntity(customer);
		customerRepository.insert(customerEntity);
	}

	public void update(CustomerDto customer) {
		CustomerEntity customerEntity = customerRepository.findByCustomerId(customer.getCustomerId());
		if (null == customerEntity) {
			throw new RuntimeException("Customer doesn't exist with customer id : " + customer.getCustomerId());
		}
		customerEntityConverter.updateCustomerEntity(customerEntity, customer);
		customerRepository.save(customerEntity);

	}

}
