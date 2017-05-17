package com.capgemini.customeronboarding.activemq.consumer.converter;

import org.springframework.stereotype.Service;

import com.capgemini.customeronboarding.activemq.message.CustomerCreatedMessage;
import com.capgemini.customeronboarding.activemq.message.CustomerUpdateMessage;
import com.capgemini.customeronboarding.dao.dto.AddressDto;
import com.capgemini.customeronboarding.dao.dto.CustomerDto;

@Service
public class CustomerMessageConverter {

	public CustomerDto getCustomerDto(CustomerUpdateMessage customerUpdatedMessage) {
		AddressDto address = null;
		if (customerUpdatedMessage.getAddress() != null) {
			address = new AddressDto();
			address.setAddressLine1(customerUpdatedMessage.getAddress().getAddressLine1());
			address.setAddressLine2(customerUpdatedMessage.getAddress().getAddressLine2());
			address.setAddressLine3(customerUpdatedMessage.getAddress().getAddressLine3());
			address.setCity(customerUpdatedMessage.getAddress().getCity());
			address.setCountry(customerUpdatedMessage.getAddress().getCountry());
			address.setPostCode(customerUpdatedMessage.getAddress().getPostCode());
		}

		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId(customerUpdatedMessage.getCustomerId());
		customerDto.setFirstName(customerUpdatedMessage.getFirstName());
		customerDto.setLastName(customerUpdatedMessage.getLastName());
		customerDto.setMobileno(customerUpdatedMessage.getMobileno());
		customerDto.setAddress(address);
		return customerDto;
	}

	public CustomerDto getCustomerDto(CustomerCreatedMessage customerCreatedMessage) {
		AddressDto address = null;
		if (customerCreatedMessage.getAddress() != null) {
			address = new AddressDto();
			address.setAddressLine1(customerCreatedMessage.getAddress().getAddressLine1());
			address.setAddressLine2(customerCreatedMessage.getAddress().getAddressLine2());
			address.setAddressLine3(customerCreatedMessage.getAddress().getAddressLine3());
			address.setCity(customerCreatedMessage.getAddress().getCity());
			address.setCountry(customerCreatedMessage.getAddress().getCountry());
			address.setPostCode(customerCreatedMessage.getAddress().getPostCode());
		}

		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId(customerCreatedMessage.getCustomerId());
		customerDto.setFirstName(customerCreatedMessage.getFirstName());
		customerDto.setLastName(customerCreatedMessage.getLastName());
		customerDto.setMobileno(customerCreatedMessage.getMobileno());
		customerDto.setAddress(address);
		return customerDto;
	}
}
