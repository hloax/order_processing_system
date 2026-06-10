package com.orderprocessing.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.event.OrderCreatedEvent;

@Service
public class AuditConsumer {

	@RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
	public void auditOrder(OrderCreatedEvent event) {
		
		System.out.println("[AUDIT] \n Order ID: " + event.getOrderId()
				+ "\n Amount: " + event.getTotalAmount());
	}
}
