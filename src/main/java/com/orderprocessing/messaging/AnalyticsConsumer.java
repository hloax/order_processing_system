package com.orderprocessing.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.orderprocessing.config.RabbitMQConfig;
import com.orderprocessing.event.OrderCreatedEvent;

@Service
public class AnalyticsConsumer {

	@RabbitListener(queues = RabbitMQConfig.ANALYTICS_QUEUE)
	public void consume(OrderCreatedEvent event) {
		
		System.out.println("[ANALYTICS]"
				+ "\n Processing order metrics for order: " + event.getOrderId());
	}
}
