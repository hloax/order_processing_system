package com.orderprocessing.inventory.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.event.OrderCreatedEvent;
import com.orderprocessing.inventory.service.InventoryService;

@Component
public class InventoryEventListener {

	private final InventoryService inventoryService;

	public InventoryEventListener(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}
	
	@RabbitListener(queues = RabbitMQConfig.INVENTORY_QUEUE)
	public void handleOrderCreated(OrderCreatedEvent event) {
		
		System.out.println("📦 Inventory received event: " + event.getOrderId());
		inventoryService.reserveStock(event);
	}
	
	
}
