package com.capgemini.customeronboarding.addcustomer.commandhandler;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.customeronboarding.aggregate.Customer;
import com.capgemini.customeronboarding.updatecustomer.command.UpdateCustomerCommand;

@Component
public class UpdateCustomerCommandHandler{// implements org.axonframework.commandhandling.CommandHandler<AddCustomerCommand>{
	
	@Autowired
	private org.axonframework.repository.Repository<Customer> repository;
	
	@CommandHandler
	public void commandHandler(UpdateCustomerCommand addCustomerCommands){
		Customer customer = repository.load(addCustomerCommands.getCustomerid());
		customer.update(addCustomerCommands);
	}

	
}
