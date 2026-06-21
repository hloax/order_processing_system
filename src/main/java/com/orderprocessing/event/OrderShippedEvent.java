package com.orderprocessing.event;

public class OrderShippedEvent {

	private Long orderId;
	
	public OrderShippedEvent() {}

	public OrderShippedEvent(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
