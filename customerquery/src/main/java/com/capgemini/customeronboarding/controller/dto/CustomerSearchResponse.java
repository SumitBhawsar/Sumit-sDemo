package com.capgemini.customeronboarding.controller.dto;

public class CustomerSearchResponse {

	private Integer customerId;

	private String firstName;

	private String lastName;

	private String mobileno;

	private AddressResponse address;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
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

	public AddressResponse getAddress() {
		return address;
	}

	public void setAddress(AddressResponse address) {
		this.address = address;
	}

}	


