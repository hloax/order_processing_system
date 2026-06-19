package com.orderprocessing.notification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.inventory.event.InventoryReservedEvent;

@Component
public class NotificationListener {

	@RabbitListener(queues = RabbitMQConfig.INVENTORY_RESERVED_QUEUE)
	public void handleInventoryReserved(
			InventoryReservedEvent event) {
		
		System.out.println(
				"Email notification sent for order: " + event.getOrderId());
	}
}
