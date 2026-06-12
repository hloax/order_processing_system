package com.orderprocessing.dto.order;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {

	private Long orderId;
	private String status;
	private BigDecimal totalAmount;
	private List<OrderItemResponse> items;
		
	public OrderResponse(Long orderId, String status, BigDecimal totalAmount, List<OrderItemResponse> items) {
		
		this.orderId = orderId;
		this.status = status;
		this.totalAmount = totalAmount;
		this.items = items;
	}

	public Long getOrderId() {
		return orderId;
	}

	public String getStatus() {
		return status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setItems(List<OrderItemResponse> items) {
		this.items = items;
	}

	public List<OrderItemResponse> getItems() {
		return items;
	}
	
}
