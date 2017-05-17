package com.capgemini.customeronboarding.activemq;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer<T> implements CommandLineRunner {
	
	

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Queue queue;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Message was sent to the Queue");
	}

	public void send(T msg) {

//		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); XMLEncoder encoder = new XMLEncoder(baos)) {
//			encoder.writeObject(msg);
//			jmsMessagingTemplate.
//			Message sg = new Message
			this.jmsTemplate.convertAndSend(this.queue, msg);
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}