package com.capgemini.customeronboarding.activemq.eventhandler;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.customeronboarding.activemq.Producer;
import com.capgemini.customeronboarding.activemq.message.AddressMessage;
import com.capgemini.customeronboarding.activemq.message.CustomerUpdateMessage;
import com.capgemini.customeronboarding.addcustomer.event.CustomerUpdatedEvent;

@Component
public class CustomerUpdatedEventHandlerMQProducer {

	@Autowired
	private Producer<CustomerUpdateMessage> customerCreatedProducer;
	
	@EventHandler
	private void handle(CustomerUpdatedEvent event){
		System.out.println("----------------------- MQ Producer eventHandler ---------------------------------------");
		CustomerUpdateMessage msg = getCustomerUpdatedMessage(event);
		customerCreatedProducer.send(msg);
	}
	
	private CustomerUpdateMessage getCustomerUpdatedMessage(CustomerUpdatedEvent customerAddedEvent) {
		AddressMessage address = null;
		if (customerAddedEvent.getAddress() != null) {
			address = new AddressMessage();
			address.setAddressLine1(customerAddedEvent.getAddress().getAddressLine1());
			address.setAddressLine2(customerAddedEvent.getAddress().getAddressLine2());
			address.setAddressLine3(customerAddedEvent.getAddress().getAddressLine3());
			address.setCity(customerAddedEvent.getAddress().getCity());
			address.setCountry(customerAddedEvent.getAddress().getCountry());
			address.setPostCode(customerAddedEvent.getAddress().getPostCode());
		}

		CustomerUpdateMessage customerDto = new CustomerUpdateMessage();
		customerDto.setCustomerId(customerAddedEvent.getCustomerId());
		customerDto.setFirstName(customerAddedEvent.getFirstName());
		customerDto.setLastName(customerAddedEvent.getLastName());
		customerDto.setMobileno(customerAddedEvent.getMobileno());
		customerDto.setAddress(address);
		return customerDto;
	}


	
}
