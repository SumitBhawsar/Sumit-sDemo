package com.capgemini.customeronboarding.dao.converter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.capgemini.customeronboarding.dao.dto.AddressDto;
import com.capgemini.customeronboarding.dao.dto.CustomerDto;
import com.capgemini.customeronboarding.dao.entity.AddressEntity;
import com.capgemini.customeronboarding.dao.entity.CustomerEntity;

@Component("customerEntityConverter")
@Qualifier("customerEntityConverter")
public class CustomerEntityConverter {

	public CustomerEntity toCustomerEntity(CustomerDto customer) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerId(customer.getCustomerId());
		customerEntity.setFirstName(customer.getFirstName());
		customerEntity.setLastName(customer.getLastName());
		customerEntity.setMobileno(customer.getMobileno());
		if (null != customer.getAddress()) {
			AddressEntity addressEntity = new AddressEntity();
			addressEntity.setAddressLine1(customer.getAddress().getAddressLine1());
			addressEntity.setAddressLine2(customer.getAddress().getAddressLine2());
			addressEntity.setAddressLine3(customer.getAddress().getAddressLine3());
			addressEntity.setCity(customer.getAddress().getCity());
			addressEntity.setCountry(customer.getAddress().getCountry());
			addressEntity.setPostCode(customer.getAddress().getPostCode());
			customerEntity.setAddress(addressEntity);
		}

		return customerEntity;
	}

	public void updateCustomerEntity(CustomerEntity customerEntity, CustomerDto customer) {
		customerEntity.setCustomerId(customer.getCustomerId());
		customerEntity.setFirstName(customer.getFirstName());
		customerEntity.setLastName(customer.getLastName());
		customerEntity.setMobileno(customer.getMobileno());
		if (null != customer.getAddress()) {
			AddressEntity addressEntity = new AddressEntity();
			addressEntity.setAddressLine1(customer.getAddress().getAddressLine1());
			addressEntity.setAddressLine2(customer.getAddress().getAddressLine2());
			addressEntity.setAddressLine3(customer.getAddress().getAddressLine3());
			addressEntity.setCity(customer.getAddress().getCity());
			addressEntity.setCountry(customer.getAddress().getCountry());
			addressEntity.setPostCode(customer.getAddress().getPostCode());
			customerEntity.setAddress(addressEntity);
		}
	}

	public CustomerDto toCustomerDto(CustomerEntity customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId(customer.getCustomerId());
		customerDto.setFirstName(customer.getFirstName());
		customerDto.setLastName(customer.getLastName());
		customerDto.setMobileno(customer.getMobileno());
		if (null != customer.getAddress()) {
			AddressDto addressDto = new AddressDto();
			addressDto.setAddressLine1(customer.getAddress().getAddressLine1());
			addressDto.setAddressLine2(customer.getAddress().getAddressLine2());
			addressDto.setAddressLine3(customer.getAddress().getAddressLine3());
			addressDto.setCity(customer.getAddress().getCity());
			addressDto.setCountry(customer.getAddress().getCountry());
			addressDto.setPostCode(customer.getAddress().getPostCode());
			customerDto.setAddress(addressDto);
		}
		return customerDto;
	}
}
