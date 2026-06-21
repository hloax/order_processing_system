package com.orderprocessing.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.*;

@Configuration
public class RabbitMQConfig {

	public static final String EXCHANGE = "order.exchange";
	public static final String AUDIT_QUEUE = "audit.queue";
	public static final String NOTIFICATION_QUEUE = "notification.queue";
	public static final String ANALYTICS_QUEUE = "analytics.queue";
	public static final String ROUTING_KEY = "order.created";
	public static final String INVENTORY_QUEUE = "inventory.queue";
	public static final String INVENTORY_EXCHANGE = "inventory.exchange";
	public static final String INVENTORY_RESERVED_QUEUE = "inventory.reserved.queue";
	public static final String INVENTORY_RESERVED_KEY = "inventory.reserved";
	public static final String ORDER_STATUS_QUEUE = "order.status.queue";
	public static final String SHIPPING_EXCHANGE = "shipping.exchange";
	public static final String SHIPPING_QUEUE = "shipping.queue";
	public static final String SHIPPING_KEY = "order.shipped";
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}
	
	@Bean
	public TopicExchange inventoryExchange() {
		return new TopicExchange(INVENTORY_EXCHANGE);
	}
	
	@Bean
	public TopicExchange shippingExchange() {
		return new TopicExchange(SHIPPING_EXCHANGE);
	}
	
	@Bean
	public Queue auditQueue() {
		return new Queue(AUDIT_QUEUE);
	}
	
	@Bean
	public Queue notificationQueue() {
		return new Queue(NOTIFICATION_QUEUE);
	}
	
	@Bean
	public Queue analyticsQueue() {
		return new Queue(ANALYTICS_QUEUE);
	}

	@Bean
	public Queue inventoryQueue() {
		return new Queue(INVENTORY_QUEUE);
	}
	
	@Bean
	public Queue inventoryReservedQueue() {
		return new Queue(INVENTORY_RESERVED_QUEUE);
	}
	
	@Bean
	public Queue orderStatusQueue() {
		return new Queue(ORDER_STATUS_QUEUE);
	}
	
	@Bean
	public Queue shippingQueue() {
		return new Queue(SHIPPING_QUEUE);
	}
	
	@Bean
	public JacksonJsonMessageConverter messageConverter() {
		return new JacksonJsonMessageConverter();
	}
	
	
	@Bean
	public Binding auditBinding(
			Queue auditQueue, TopicExchange exchange) {
		
		return BindingBuilder
				.bind(auditQueue)
				.to(exchange)
				.with(ROUTING_KEY);
	}
	
	@Bean
	public Binding notificationBinding(
			Queue notificationQueue, TopicExchange exchange) {
		
		return BindingBuilder
				.bind(notificationQueue)
				.to(exchange)
				.with(ROUTING_KEY);
	}
	
	@Bean
	public Binding analyticsBinding(
			Queue analyticsQueue, TopicExchange exchange) {
		
		return BindingBuilder
				.bind(analyticsQueue)
				.to(exchange)
				.with(ROUTING_KEY);
	}
	
	@Bean
	public Binding inventoryBinding(
			Queue inventoryQueue, TopicExchange exchange) {
		
		return BindingBuilder
				.bind(inventoryQueue)
				.to(exchange)
				.with(ROUTING_KEY);
	}
	
	@Bean
	public Binding inventoryReservedBinding(
			Queue inventoryReservedQueue,
			TopicExchange inventoryExchange) {
		
		return BindingBuilder
				.bind(inventoryReservedQueue)
				.to(inventoryExchange)
				.with(INVENTORY_RESERVED_KEY);
	}
	
	@Bean
	public Binding orderStatusBinding(
			Queue orderStatusQueue,
			TopicExchange inventoryExchange) {
		
		return BindingBuilder
				.bind(orderStatusQueue)
				.to(inventoryExchange)
				.with(INVENTORY_RESERVED_KEY);
	}
	
	@Bean
	public Binding shippingBinding(
			Queue shippingQueue,
			TopicExchange shippingExchange) {
		
		return BindingBuilder
				.bind(shippingQueue)
				.to(shippingExchange)
				.with(SHIPPING_KEY);
	}
}
