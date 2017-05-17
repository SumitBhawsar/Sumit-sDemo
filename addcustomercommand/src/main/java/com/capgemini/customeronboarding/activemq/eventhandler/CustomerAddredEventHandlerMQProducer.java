package com.capgemini.customeronboarding.activemq.eventhandler;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.customeronboarding.activemq.Producer;
import com.capgemini.customeronboarding.activemq.message.AddressMessage;
import com.capgemini.customeronboarding.activemq.message.CustomerCreatedMessage;
import com.capgemini.customeronboarding.addcustomer.event.CustomerAddedEvent;

@Component
public class CustomerAddredEventHandlerMQProducer {

	@Autowired
	private Producer<CustomerCreatedMessage> customerCreatedProducer;
	
	@EventHandler
	private void handle(CustomerAddedEvent event){
		System.out.println("----------------------- MQ Producer eventHandler ---------------------------------------");
		CustomerCreatedMessage msg = getCustomerAddedMessage(event);
		customerCreatedProducer.send(msg); 
	}
	
	private CustomerCreatedMessage getCustomerAddedMessage(CustomerAddedEvent customerAddedEvent) {
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

		CustomerCreatedMessage customerDto = new CustomerCreatedMessage();
		customerDto.setCustomerId(customerAddedEvent.getCustomerId());
		customerDto.setFirstName(customerAddedEvent.getFirstName());
		customerDto.setLastName(customerAddedEvent.getLastName());
		customerDto.setMobileno(customerAddedEvent.getMobileno());
		customerDto.setAddress(address);
		return customerDto;
	}


	
}
