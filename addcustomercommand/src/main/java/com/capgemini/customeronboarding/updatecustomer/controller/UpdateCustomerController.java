package com.capgemini.customeronboarding.updatecustomer.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.customeronboarding.aggregate.member.Address;
import com.capgemini.customeronboarding.updatecustomer.command.UpdateCustomerCommand;
import com.capgemini.customeronboarding.updatecustomer.controller.dto.UpdateCustomerRequest;

@RestController
public class UpdateCustomerController {

	@Autowired
	private CommandGateway commandGateway;

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public void updateCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest) {

		UpdateCustomerCommand addCustomerCommand = getAddCustomerCommand(updateCustomerRequest);
		commandGateway.sendAndWait(addCustomerCommand);
	}

	private UpdateCustomerCommand getAddCustomerCommand(UpdateCustomerRequest addCustomerRequest) {
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

		UpdateCustomerCommand updateCustomerCommand = new UpdateCustomerCommand(addCustomerRequest.getCustomerId(),
				addCustomerRequest.getFirstName(), addCustomerRequest.getLastName(), addCustomerRequest.getMobileno(),
				address);
		return updateCustomerCommand;
	}
}
