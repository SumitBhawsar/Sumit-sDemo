package com.capgemini.customeronboarding.replay.eventhandler;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.replay.ReplayAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.capgemini.customeronboarding.addcustomer.event.CustomerAddedEvent;
import com.capgemini.customeronboarding.addcustomer.event.CustomerUpdatedEvent;
import com.capgemini.customeronboarding.dao.dto.AddressDto;
import com.capgemini.customeronboarding.dao.dto.CustomerDto;
import com.capgemini.customeronboarding.replay.dao.CustomerDao;

@Component("replayEventHandler")
@Qualifier("replayEventHandler")
public class ReplayEventHandler implements ReplayAware {

	private List<String> audit = new ArrayList<>();

	@Autowired
	private CustomerDao customerDao;

	@EventHandler
	public void customerAddedEventHandler(CustomerUpdatedEvent event) {
		System.out.println("-----------------------relpay eventHandler ---------------------------------------");
		customerDao.update(getAddCustomerCommand(event));
		String auditMsg = String.format("Customer updated customer id : {%s},  firstname {%s} on lastname {%s}",
				event.getCustomerId(), event.getFirstName(), event.getLastName());
		audit.add(auditMsg);
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

	
	@EventHandler
	public void customerAddedEventHandler(CustomerAddedEvent event) {
		System.out.println("----------------------- Relpay EventHandler ---------------------------------------");
		
		customerDao.save(getAddCustomerCommand(event));
		String auditMsg = String.format("Customer added customer id : {%s},  firstname {%s} on lastname {%s}",
				event.getCustomerId(), event.getFirstName(), event.getLastName());
		audit.add(auditMsg);
	}

	private CustomerDto getAddCustomerCommand(CustomerAddedEvent customerAddedEvent) {
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
	@Override
	public void beforeReplay() {
		audit.clear();
	}

	@Override
	public void afterReplay() {
	}

	@Override
	public void onReplayFailed(Throwable cause) {
		// TODO Auto-generated method stub

	}

	public List<String> getAudit() {
		return audit;
	}
}
