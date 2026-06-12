package com.orderprocessing.dto.order;

import jakarta.validation.constraints.*;

public class OrderItemRequest {

	@NotNull
	private Long productId;
	
	@Min(1)
	private Integer quantity;
	
	public OrderItemRequest() {}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
