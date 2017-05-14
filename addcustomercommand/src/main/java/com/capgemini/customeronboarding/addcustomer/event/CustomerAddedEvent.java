package com.capgemini.customeronboarding.addcustomer.event;

import com.capgemini.customeronboarding.aggregate.member.Address;

public class CustomerAddedEvent {

	private int customerId;
	
	private String firstName;

	private String lastName;

	private String mobileno;

	private Address address;
	
	@SuppressWarnings("unused")
	private CustomerAddedEvent(){
		
	}

	public CustomerAddedEvent(int customerId, String firstName, String lastName, String mobileno, Address address) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileno = mobileno;
		this.address = address;
	}

	
	public int getCustomerId() {
		return customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMobileno() {
		return mobileno;
	}

	public Address getAddress() {
		return address;
	}

}
