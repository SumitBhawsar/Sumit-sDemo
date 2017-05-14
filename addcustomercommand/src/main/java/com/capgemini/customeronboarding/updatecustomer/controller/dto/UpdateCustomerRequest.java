package com.capgemini.customeronboarding.updatecustomer.controller.dto;

import com.capgemini.customeronboarding.addcustomer.controller.dto.AddressDto;

public class UpdateCustomerRequest {
	private int customerId;

	private String firstName;

	private String lastName;

	private String mobileno;

	private AddressDto address;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

}
