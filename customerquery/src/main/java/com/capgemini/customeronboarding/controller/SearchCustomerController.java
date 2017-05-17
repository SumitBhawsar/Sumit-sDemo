package com.capgemini.customeronboarding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.customeronboarding.controller.dto.CustomerSearchRequest;
import com.capgemini.customeronboarding.controller.dto.CustomerSearchResponse;
import com.capgemini.customeronboarding.service.SearchCustomerService;

@RestController
public class SearchCustomerController {

	@Autowired
	private SearchCustomerService searchCustomerService;
	
	@RequestMapping(value="/getCustomers", method = RequestMethod.POST)
	@ResponseBody
	public List<CustomerSearchResponse> getCustomers(@RequestBody CustomerSearchRequest searchRequest){
		return searchCustomerService.searchCustomer(searchRequest);
		
	}
}
