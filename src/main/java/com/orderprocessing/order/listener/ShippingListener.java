package com.orderprocessing.order.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.entity.Order;
import com.orderprocessing.entity.OrderStatus;
import com.orderprocessing.event.OrderShippedEvent;
import com.orderprocessing.repository.OrderRepository;
import com.orderprocessing.service.OrderAuditService;

@Component
public class ShippingListener {

	private final OrderRepository orderRepository;
	private final OrderAuditService auditService;

	public ShippingListener(
			OrderRepository orderRepository,
			OrderAuditService auditService) {
		
		this.orderRepository = orderRepository;
		this.auditService = auditService;
	}
	
	@RabbitListener(queues = RabbitMQConfig.SHIPPING_QUEUE)
	public void handleShipment(OrderShippedEvent event) {
		
		Order order =
				orderRepository
					.findById(event.getOrderId())
					.orElseThrow();
		
		order.setStatus(OrderStatus.SHIPPED);
		
		auditService.recordStatus(
				order.getId(), OrderStatus.SHIPPED.name());
		
		orderRepository.save(order);
		
		System.out.println("Order shipped: " + event.getOrderId());
	}
}
