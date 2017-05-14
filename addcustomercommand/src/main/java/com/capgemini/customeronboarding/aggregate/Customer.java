package com.capgemini.customeronboarding.aggregate;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import com.capgemini.customeronboarding.addcustomer.command.AddCustomerCommand;
import com.capgemini.customeronboarding.addcustomer.event.CustomerAddedEvent;
import com.capgemini.customeronboarding.addcustomer.event.CustomerUpdatedEvent;
import com.capgemini.customeronboarding.aggregate.member.Address;
import com.capgemini.customeronboarding.updatecustomer.command.UpdateCustomerCommand;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Customer extends AbstractAnnotatedAggregateRoot<Integer> {

	private static final long serialVersionUID = 4161182599300799745L;

	@AggregateIdentifier
	private Integer customerId;

	private String firstName;

	private String lastName;

	private String mobileno;

	private Address address;

	public Customer() {
	}

	@CommandHandler
	public Customer(AddCustomerCommand addCustomerCommands) {
		apply(new CustomerAddedEvent(addCustomerCommands.getCustomerid(), addCustomerCommands.getFirstName(),
				addCustomerCommands.getLastName(), addCustomerCommands.getMobileno(),
				addCustomerCommands.getAddress()));
	}

	@CommandHandler
	public Customer(UpdateCustomerCommand updateCustomerCommands) {
		apply(new CustomerUpdatedEvent(updateCustomerCommands.getCustomerid(), updateCustomerCommands.getFirstName(),
				updateCustomerCommands.getLastName(), updateCustomerCommands.getMobileno(),
				updateCustomerCommands.getAddress()));
	}

	@Override
	public Integer getIdentifier() {
		return customerId;
	}

	public Integer getCustomerId() {
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

	@EventSourcingHandler
	public void handleCustomerCreatedEvent(CustomerAddedEvent customerAddedEvent) {
		this.customerId = customerAddedEvent.getCustomerId();
		this.firstName = customerAddedEvent.getFirstName();
		this.lastName = customerAddedEvent.getLastName();
		this.mobileno = customerAddedEvent.getMobileno();
		this.address = customerAddedEvent.getAddress();
	}

	public void update(UpdateCustomerCommand customerUpdateEvent) {
		apply(new CustomerUpdatedEvent(customerUpdateEvent.getCustomerid(), customerUpdateEvent.getFirstName(), 
				customerUpdateEvent.getLastName(), customerUpdateEvent.getMobileno(), customerUpdateEvent.getAddress()));
	}

	@EventSourcingHandler
	public void handleCustomerupdatedEvent(CustomerUpdatedEvent customerUpdateEvent) {
		this.customerId = customerUpdateEvent.getCustomerId();
		this.firstName = customerUpdateEvent.getFirstName();
		this.lastName = customerUpdateEvent.getLastName();
		this.mobileno = customerUpdateEvent.getMobileno();
		this.address = customerUpdateEvent.getAddress();
	}

}
