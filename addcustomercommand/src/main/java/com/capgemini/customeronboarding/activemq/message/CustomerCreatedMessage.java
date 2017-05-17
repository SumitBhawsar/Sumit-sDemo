package com.capgemini.customeronboarding.activemq.message;

import java.io.Serializable;

public class CustomerCreatedMessage implements Serializable{
	private static final long serialVersionUID = 2159947641595063045L;

	private Integer customerId;

	private String firstName;

	private String lastName;

	private String mobileno;

	private AddressMessage address;
	
	private boolean replayEvent;

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

	public AddressMessage getAddress() {
		return address;
	}

	public void setAddress(AddressMessage address) {
		this.address = address;
	}


	public boolean isReplayEvent() {
		return replayEvent;
	}

	public void setReplayEvent(boolean replayEvent) {
		this.replayEvent = replayEvent;
	}
}
