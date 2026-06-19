package com.orderprocessing.event;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEvent {

	private Long orderId;
	
	private String customerEmail;
	
	private BigDecimal totalAmount;
	
	private List<OrderItemEvent> items;
	
	public OrderCreatedEvent() {}

	public OrderCreatedEvent(Long orderId, String customerEmail, BigDecimal totalAmount, List<OrderItemEvent> items) {
		
		this.orderId = orderId;
		this.customerEmail = customerEmail;
		this.totalAmount = totalAmount;
		this.items = items;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderItemEvent> getItems() {
		return items;
	}

	public void setItems(List<OrderItemEvent> items) {
		this.items = items;
	}
	
}
