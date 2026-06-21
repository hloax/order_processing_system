package com.orderprocessing.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.event.OrderShippedEvent;

@Component
public class ShippingEventPublisher {

	private final RabbitTemplate rabbitTemplate;

	public ShippingEventPublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void publishOrderShipped(OrderShippedEvent event) {
		
		rabbitTemplate.convertAndSend(
				RabbitMQConfig.SHIPPING_EXCHANGE,
				RabbitMQConfig.SHIPPING_KEY,
				event);
		
	}
}
