/*package com.capgemini.customeronboarding.addcustomer.commandhandler;

import java.util.Random;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.customeronboarding.addcustomer.aggregate.Customer;
import com.capgemini.customeronboarding.addcustomer.command.AddCustomerCommand;

@Component
public class AddCustomerCommandHandler{// implements org.axonframework.commandhandling.CommandHandler<AddCustomerCommand>{
	
	@Autowired
	private org.axonframework.repository.Repository<Customer> repository;
	
	@Autowired
	
	
	@CommandHandler
	public Customer commandHandler(AddCustomerCommand addCustomerCommands){
		Customer customer = new Customer(new Random().nextInt(), addCustomerCommands.getFirstName(), addCustomerCommands.getLastName(), addCustomerCommands.getMobileno(), addCustomerCommands.getAddress());
		repository.add(customer);
		return customer;
	}

	@Override
	public Object handle(CommandMessage<AddCustomerCommand> commandMessage, UnitOfWork unitOfWork) throws Throwable {
		AddCustomerCommand addCustomerCommand = commandMessage.getPayload();
		Customer customer = new Customer(addCustomerCommand.getFirstName(), addCustomerCommand.getLastName(), addCustomerCommand.getMobileno(), addCustomerCommand.getAddress());
		return customer;
	}
	
}
*/