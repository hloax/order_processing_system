package com.orderprocessing.service;

import org.springframework.stereotype.Service;

import com.orderprocessing.event.OrderShippedEvent;
import com.orderprocessing.messaging.ShippingEventPublisher;

@Service
public class ShippingService {

	private final ShippingEventPublisher shippingEventPublisher;

	public ShippingService(ShippingEventPublisher shippingEventPublisher) {
		this.shippingEventPublisher = shippingEventPublisher;
	}
	
	public void processShipment(Long orderId) {
		
		System.out.println(
				"Preparing shipment for order " + orderId);
		
		OrderShippedEvent event = new OrderShippedEvent(orderId);
		
		shippingEventPublisher.publishOrderShipped(event);
	}
}
