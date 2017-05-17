package com.capgemini.customeronboarding.activemq.message;

import java.io.Serializable;

public class AddressMessage implements Serializable{
	private static final long serialVersionUID = -6375898328752393833L;
	

	private String addressLine1;

	private String addressLine2;

	private String addressLine3;

	private String postCode;

	private String city;

	private String country;

	private boolean replayEvent;

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isReplayEvent() {
		return replayEvent;
	}

	public void setReplayEvent(boolean replayEvent) {
		this.replayEvent = replayEvent;
	}

}
