package com.orderprocessing.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.event.OrderCreatedEvent;
import com.orderprocessing.inventory.service.InventoryService;

@Service
public class AuditConsumer {

	private static final Logger log =
			LoggerFactory.getLogger(InventoryService.class);
	
	@RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
	public void auditOrder(OrderCreatedEvent event) {
		
		log.info("Audit record created for order {}", event.getOrderId());
	}
}
