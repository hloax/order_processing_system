package com.orderprocessing.order.listener;

import com.orderprocessing.service.OrderAuditService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.entity.Order;
import com.orderprocessing.entity.OrderStatus;
import com.orderprocessing.inventory.event.InventoryReservedEvent;
import com.orderprocessing.repository.OrderRepository;
import com.orderprocessing.service.ShippingService;

@Component
public class OrderStatusListener {

	private final OrderAuditService auditService;
	private final OrderRepository orderRepository;
	private final ShippingService shippingService;

	public OrderStatusListener(
			OrderRepository orderRepository,
			ShippingService shippingService,
			OrderAuditService auditService) {
		
		this.orderRepository = orderRepository;
		this.shippingService = shippingService;
		this.auditService = auditService;
	}
	
	@RabbitListener(queues = RabbitMQConfig.ORDER_STATUS_QUEUE)
	public void udpateOrderStatus(InventoryReservedEvent event) {
		
		Order order =
				orderRepository
					.findById(event.getOrderId())
					.orElseThrow();
		
		order.setStatus(OrderStatus.PROCESSING);
		
		shippingService.processShipment(event.getOrderId());
		auditService.recordStatus(
				order.getId(), OrderStatus.PROCESSING.name());
		
		orderRepository.save(order);
		
		System.out.println("Order moved to PROCESSING");
	}
}
