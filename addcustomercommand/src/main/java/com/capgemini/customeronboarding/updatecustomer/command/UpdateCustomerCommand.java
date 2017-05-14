package com.capgemini.customeronboarding.updatecustomer.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import com.capgemini.customeronboarding.aggregate.member.Address;


public class UpdateCustomerCommand {
	
	@TargetAggregateIdentifier
	private int customerid;

	private String firstName;

	private String lastName;

	private String mobileno;

	private Address address;

	@SuppressWarnings("unused")
	private UpdateCustomerCommand() {

	}

	public UpdateCustomerCommand(int customerid, String firstName, String lastName, String mobileno, Address address) {
		super();
		this.customerid = customerid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileno = mobileno;
		this.address = address;
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

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	
}
