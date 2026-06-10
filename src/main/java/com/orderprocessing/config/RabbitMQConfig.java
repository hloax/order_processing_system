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
	
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
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
}
