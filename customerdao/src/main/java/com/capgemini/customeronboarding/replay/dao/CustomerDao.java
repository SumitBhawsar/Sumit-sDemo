package com.capgemini.customeronboarding.replay.dao;

import com.capgemini.customeronboarding.dao.dto.CustomerDto;

public interface CustomerDao {

	void save(CustomerDto customer);
	
	void update(CustomerDto customer);
}
