package com.capgemini.customeronboarding.addcustomer.controller;

import java.util.Random;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.customeronboarding.addcustomer.command.AddCustomerCommand;
import com.capgemini.customeronboarding.addcustomer.controller.dto.AddCustomerRequest;
import com.capgemini.customeronboarding.aggregate.member.Address;

@RestController
public class AddCustomerController {

	@Autowired
	private CommandGateway commandGateway;

	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public int addCustomer(@RequestBody AddCustomerRequest addCustomerRequest) {
		AddCustomerCommand addCustomerCommand = getAddCustomerCommand(addCustomerRequest);
		return commandGateway.sendAndWait(addCustomerCommand);
	}

	private AddCustomerCommand getAddCustomerCommand(AddCustomerRequest addCustomerRequest) {
		Address address = null;
		if (addCustomerRequest.getAddress() != null) {
			address = new Address();
			address.setAddressLine1(addCustomerRequest.getAddress().getAddressLine1());
			address.setAddressLine2(addCustomerRequest.getAddress().getAddressLine2());
			address.setAddressLine3(addCustomerRequest.getAddress().getAddressLine3());
			address.setCity(addCustomerRequest.getAddress().getCity());
			address.setCountry(addCustomerRequest.getAddress().getCountry());
			address.setPostCode(addCustomerRequest.getAddress().getPostCode());
		}

		AddCustomerCommand addCustomerCommand = new AddCustomerCommand(new Random().nextInt(Integer.MAX_VALUE), addCustomerRequest.getFirstName(),
				addCustomerRequest.getLastName(), addCustomerRequest.getMobileno(), address);
		return addCustomerCommand;
	}
}
