package com.orderprocessing.inventory.service;

import org.springframework.stereotype.Service;

import com.orderprocessing.entity.Product;
import com.orderprocessing.event.*;
import com.orderprocessing.inventory.event.InventoryReservedEvent;
import com.orderprocessing.inventory.messaging.InventoryEventPublisher;
import com.orderprocessing.repository.ProductRepository;

@Service
public class InventoryService {

	private final ProductRepository productRepository;
	private final InventoryEventPublisher inventoryEventPublisher;
	
	public InventoryService(ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.inventoryEventPublisher = null;
	}

	public void reserveStock(OrderCreatedEvent event) {
		
		for (OrderItemEvent item : event.getItems()) {
		
			Product product =
					productRepository.findById(
							item.getProductId())
					.orElseThrow();
			
			product.setStockQuantity(
					product.getStockQuantity()
					- item.getQuantity());
			
			productRepository.save(product);
		}
		
		System.out.println(
				"Inventory reserved for order " + event.getOrderId());
	
		InventoryReservedEvent reservedEvent =
				new InventoryReservedEvent(event.getOrderId());
		
		inventoryEventPublisher
				.publishInventoryReserved(reservedEvent);
		
		System.out.println("Published inventory reserved event");
	}
}
