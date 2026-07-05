package com.orderprocessing.inventory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.orderprocessing.entity.Product;
import com.orderprocessing.event.*;
import com.orderprocessing.inventory.event.InventoryReservedEvent;
import com.orderprocessing.inventory.messaging.InventoryEventPublisher;
import com.orderprocessing.repository.ProductRepository;

@Service
public class InventoryService {

	private static final Logger log =
			LoggerFactory.getLogger(InventoryService.class);
	private final ProductRepository productRepository;
	private final InventoryEventPublisher inventoryEventPublisher;
	
	public InventoryService(ProductRepository productRepository,
			InventoryEventPublisher inventoryEventPublisher) {
		this.productRepository = productRepository;
		this.inventoryEventPublisher = inventoryEventPublisher;
	}

	public void reserveStock(OrderCreatedEvent event) {
		
		log.info("Reserving stock for order {}", event.getOrderId());
		
		/*for (OrderItemEvent item : event.getItems()) {
		
			Product product =
					productRepository.findById(
							item.getProductId())
					.orElseThrow();
			
			product.setStockQuantity(
					product.getStockQuantity()
					- item.getQuantity());
			
			productRepository.save(product);
		}*/
		
		log.info("Inventory reserved for order {}", event.getOrderId());
	
		InventoryReservedEvent reservedEvent =
				new InventoryReservedEvent(
						event.getOrderId(), "PROCESSING");
		
		inventoryEventPublisher
				.publishInventoryReserved(reservedEvent);
		
		log.info("Published InventoryReservedEvent for order {}", event.getOrderId());
	}
}
