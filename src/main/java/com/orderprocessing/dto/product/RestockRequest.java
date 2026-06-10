package com.orderprocessing.dto.product;

import jakarta.validation.constraints.Positive;

public class RestockRequest {

	@Positive
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
