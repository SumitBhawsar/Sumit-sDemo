package com.capgemini.customeronboarding.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.customeronboarding.controller.dto.AddressResponse;
import com.capgemini.customeronboarding.controller.dto.CustomerSearchRequest;
import com.capgemini.customeronboarding.controller.dto.CustomerSearchResponse;
import com.capgemini.customeronboarding.dao.CustomerDao;
import com.capgemini.customeronboarding.dao.dto.CustomerDto;
import com.capgemini.customeronboarding.dao.dto.CustomerSearchDto;

@Service
public class SearchCustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	public List<CustomerSearchResponse> searchCustomer(CustomerSearchRequest searchRequest){
		 List<CustomerDto> list = customerDao.search(getSearchDto(searchRequest));
		 return getResponse(list);
	}
	
	private CustomerSearchDto getSearchDto(CustomerSearchRequest searchRequest){
		CustomerSearchDto customer = new CustomerSearchDto();
		customer.setFirstName(searchRequest.getFirstName());
		customer.setLastName(searchRequest.getLastName());
		customer.setMobileNumber(searchRequest.getMobileno());
		return customer;
	}

	private List<CustomerSearchResponse> getResponse(List<CustomerDto> customerDtos){
		List<CustomerSearchResponse> searchResponses = new ArrayList<>();
		for(CustomerDto customer :  customerDtos){
			CustomerSearchResponse response  = new CustomerSearchResponse();
			response.setCustomerId(customer.getCustomerId());
			response.setFirstName(customer.getFirstName());
			response.setLastName(customer.getLastName());
			response.setMobileno(customer.getMobileno());
			if (null != customer.getAddress()) {
				AddressResponse addressDto = new AddressResponse();
				addressDto.setAddressLine1(customer.getAddress().getAddressLine1());
				addressDto.setAddressLine2(customer.getAddress().getAddressLine2());
				addressDto.setAddressLine3(customer.getAddress().getAddressLine3());
				addressDto.setCity(customer.getAddress().getCity());
				addressDto.setCountry(customer.getAddress().getCountry());
				addressDto.setPostCode(customer.getAddress().getPostCode());
				response.setAddress(addressDto);
			}
			searchResponses.add(response);
		}

		return searchResponses;
	}
}
