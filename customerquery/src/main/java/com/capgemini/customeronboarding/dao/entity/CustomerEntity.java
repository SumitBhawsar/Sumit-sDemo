package com.capgemini.customeronboarding.dao.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CUSTOMER_QUERY")
@Entity
public class CustomerEntity {

	@Id
	private Integer customerId;

	private String firstName;

	private String lastName;

	private String mobileno;

	@OneToOne
	private AddressEntity address;

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

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	
}

