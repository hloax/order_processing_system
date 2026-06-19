package com.orderprocessing.inventory.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.orderprocessing.inventory.event.InventoryReservedEvent;

@Component
public class InventoryEventPublisher {

	private final RabbitTemplate rabbitTemplate;

	public InventoryEventPublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void publishInventoryReserved(
			InventoryReservedEvent event) {
		
		rabbitTemplate.convertAndSend(
				"inventory.exchange",
				"inventory.reserved",
				event);
	}
}
