package com.orderprocessing.dto.order;

import java.math.BigDecimal;

public class OrderItemResponse {

	private Long productId;
	private String productName;
	private Integer quantity;
	private BigDecimal price;
	
	public OrderItemResponse() {}
	
	public OrderItemResponse(Long productId, String productName, Integer quantity, BigDecimal price) {
		
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
