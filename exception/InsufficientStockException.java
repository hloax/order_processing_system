package com.orderprocessing.exception;

public class InsufficientStockException extends RuntimeException {

	public InsufficientStockException(String productName) {
		
		super("Insufficient stock for product: " + productName);
	}
}
