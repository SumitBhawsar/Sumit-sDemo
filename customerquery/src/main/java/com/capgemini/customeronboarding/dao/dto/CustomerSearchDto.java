package com.capgemini.customeronboarding.dao.dto;

public class CustomerSearchDto {

	
	private String firstName;
	
	private String lastName;
	
	private String mobileNumber;

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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "CustomerSearchDto [firstName=" + firstName + ", lastName=" + lastName + ", mobileNumber=" + mobileNumber
				+ "]";
	}
	
}