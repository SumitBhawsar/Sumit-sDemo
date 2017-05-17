package com.capgemini.customeronboarding.dao;

import java.util.List;

import com.capgemini.customeronboarding.dao.dto.CustomerDto;
import com.capgemini.customeronboarding.dao.dto.CustomerSearchDto;

public interface CustomerDao {

	void save(CustomerDto customer);
	
	void update(CustomerDto customer);
	
	List<CustomerDto> search(CustomerSearchDto customer);
}
