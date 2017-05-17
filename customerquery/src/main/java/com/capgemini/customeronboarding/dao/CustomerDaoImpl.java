package com.capgemini.customeronboarding.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.customeronboarding.dao.converter.CustomerEntityConverter;
import com.capgemini.customeronboarding.dao.dto.CustomerDto;
import com.capgemini.customeronboarding.dao.dto.CustomerSearchDto;
import com.capgemini.customeronboarding.dao.entity.CustomerEntity;
import com.capgemini.customeronboarding.dao.repository.CustomerRepository;

@Service("customerDao")
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private CustomerEntityConverter customerEntityConverter;

	@Autowired
	private CustomerRepository customerRepository;

	public void save(CustomerDto customer) {
		CustomerEntity customerEntity = customerEntityConverter.toCustomerEntity(customer);
		customerRepository.save(customerEntity);
	}

	public void update(CustomerDto customer) {
		CustomerEntity customerEntity = customerRepository.findByCustomerId(customer.getCustomerId());
		if (null == customerEntity) {
			throw new RuntimeException("Customer doesn't exist with customer id : " + customer.getCustomerId());
		}
		customerEntityConverter.updateCustomerEntity(customerEntity, customer);
		customerRepository.save(customerEntity);

	}

	@Override
	public List<CustomerDto> search(CustomerSearchDto customer) {

		List<CustomerEntity> customerEntities = customerRepository.findByFirstNameAndLastNameAndMobileno(
				customer.getFirstName(), customer.getLastName(), customer.getMobileNumber());
		if (null == customerEntities || customerEntities.isEmpty()) {
			throw new RuntimeException("Customer doesn't exist with parameters: " + customer);
		}
		return customerEntityConverter.toCustomerDto(customerEntities);

	}

}
