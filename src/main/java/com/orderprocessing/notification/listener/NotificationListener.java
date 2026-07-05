package com.orderprocessing.notification.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.inventory.event.InventoryReservedEvent;
import com.orderprocessing.inventory.service.InventoryService;

@Component
public class NotificationListener {

	private static final Logger log =
			LoggerFactory.getLogger(InventoryService.class);
	
	@RabbitListener(queues = RabbitMQConfig.INVENTORY_RESERVED_QUEUE)
	public void handleInventoryReserved(
			InventoryReservedEvent event) {
		
		log.info("Email notification sent for order {}", event.getOrderId());
	}
}
