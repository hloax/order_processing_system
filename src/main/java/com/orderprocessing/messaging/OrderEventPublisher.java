package com.orderprocessing.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.event.OrderCreatedEvent;

@Service
public class OrderEventPublisher {

	private final RabbitTemplate rabbitTemplate;

	public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void publishOrderCreated(OrderCreatedEvent event) {
		
		rabbitTemplate.convertAndSend(
				RabbitMQConfig.EXCHANGE,
				RabbitMQConfig.ROUTING_KEY,
				event);
	}
}
