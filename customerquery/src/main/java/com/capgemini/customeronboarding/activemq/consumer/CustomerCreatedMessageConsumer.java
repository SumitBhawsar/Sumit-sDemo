package com.capgemini.customeronboarding.activemq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.capgemini.customeronboarding.activemq.consumer.converter.CustomerMessageConverter;
import com.capgemini.customeronboarding.activemq.message.CustomerCreatedMessage;
import com.capgemini.customeronboarding.activemq.message.CustomerUpdateMessage;
import com.capgemini.customeronboarding.dao.CustomerDao;

@Component
public class CustomerCreatedMessageConsumer implements MessageListener {

	@Autowired
	private MessageConverter jacksonJmsMessageConverter;

	@Autowired
	private CustomerDao customerDao; 
	
	@Autowired
	private CustomerMessageConverter messageConverter;
	
	@Override
	public void onMessage(Message message) {

		try {
			Object obj = jacksonJmsMessageConverter.fromMessage(message);
			if (obj instanceof CustomerCreatedMessage) {
				System.out.println("Consumed message: " + obj);
				customerDao.save(messageConverter.getCustomerDto((CustomerCreatedMessage) obj));
			}
			else if (obj instanceof CustomerUpdateMessage) {
				System.out.println("Consumed message: " + obj);
				customerDao.update(messageConverter.getCustomerDto((CustomerUpdateMessage) obj));
			}
		} catch (MessageConversionException | JMSException e) {
			e.printStackTrace();
		}

	}



	
}
