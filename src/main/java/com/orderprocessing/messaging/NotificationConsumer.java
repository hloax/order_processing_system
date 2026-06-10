package com.orderprocessing.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.event.OrderCreatedEvent;

@Service
public class NotificationConsumer {

	@RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
	public void consume(OrderCreatedEvent event) {
		
		System.out.println("[NOTIFICATION] \n "
				+ "Email would be sent to: " + event.getCustomerEmail());
	}
}
