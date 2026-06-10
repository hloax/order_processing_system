package com.orderprocessing.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public class ProductRequest {

	@NotBlank
	private String name;
	
	private String description;
	
	@NotNull
	@Positive
	private BigDecimal price;
	
	@NotNull
	@PositiveOrZero
	private Integer stockQuantity;
	
	@NotBlank
	private String category;
	
	public ProductRequest() {}
	
		public ProductRequest(String name, String description, BigDecimal price, Integer stockQuantity, String category) {
		
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.category = category;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
