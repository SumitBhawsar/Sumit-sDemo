package com.capgemini.customeronboarding.eventhandler.updatecustomer;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.capgemini.customeronboarding.addcustomer.event.CustomerUpdatedEvent;
import com.capgemini.customeronboarding.dao.CustomerDao;
import com.capgemini.customeronboarding.dao.dto.AddressDto;
import com.capgemini.customeronboarding.dao.dto.CustomerDto;

@Component("customerUpdatedEventHandler")
@Qualifier("customerUpdatedEventHandler")
public class CustomerUpdatedEventHandler{

	@Autowired
	private CustomerDao customerDao;

	@EventHandler
	public void customerAddedEventHandler(CustomerUpdatedEvent event) {
		System.out.println("----------------------- EventHandler ---------------------------------------");
		customerDao.update(getAddCustomerCommand(event));
	}

	private CustomerDto getAddCustomerCommand(CustomerUpdatedEvent customerAddedEvent) {
		AddressDto address = null;
		if (customerAddedEvent.getAddress() != null) {
			address = new AddressDto();
			address.setAddressLine1(customerAddedEvent.getAddress().getAddressLine1());
			address.setAddressLine2(customerAddedEvent.getAddress().getAddressLine2());
			address.setAddressLine3(customerAddedEvent.getAddress().getAddressLine3());
			address.setCity(customerAddedEvent.getAddress().getCity());
			address.setCountry(customerAddedEvent.getAddress().getCountry());
			address.setPostCode(customerAddedEvent.getAddress().getPostCode());
		}

		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId(customerAddedEvent.getCustomerId());
		customerDto.setFirstName(customerAddedEvent.getFirstName());
		customerDto.setLastName(customerAddedEvent.getLastName());
		customerDto.setMobileno(customerAddedEvent.getMobileno());
		customerDto.setAddress(address);
		return customerDto;
	}

}
