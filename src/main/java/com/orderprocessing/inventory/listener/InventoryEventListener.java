package com.orderprocessing.inventory.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.event.OrderCreatedEvent;
import com.orderprocessing.inventory.service.InventoryService;

@Component
public class InventoryEventListener {

	private static final Logger log =
			LoggerFactory.getLogger(InventoryService.class);
	
	@RabbitListener(queues = RabbitMQConfig.INVENTORY_QUEUE)
	public void handleOrderCreated(OrderCreatedEvent event) {
		
		log.info("Received OrderCreatedEvent for order {}", event.getOrderId());
	
	}
	
}
