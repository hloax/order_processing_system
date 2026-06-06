package com.orderprocessing.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderprocessing.dto.product.*;
import com.orderprocessing.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		
		this.productService = productService;
	}
	
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(
			@Valid @RequestBody ProductRequest request) {
		return ResponseEntity.ok(productService.createProduct(request));
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ProductResponse> updateProduct(
				@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
		
		return ResponseEntity.ok(productService.updateProduct(id, request));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		
		productService.deleteProduct(id);
		
		return ResponseEntity.ok("Product deleted successfully");
	}
	
	@GetMapping("/paged")
	public ResponseEntity<Page<ProductResponse>> getProducts(
				@RequestParam(defaultValue= "0") int page,
				@RequestParam(defaultValue = "10") int size,
				@RequestParam(defaultValue = "id") String sortBy) {
		
		return ResponseEntity.ok(productService.getProducts(page, size, sortBy));
	}
	
	@PatchMapping("/{id}/restock")
	public ResponseEntity<ProductResponse> restockProduct(
				@PathVariable Long id, @Valid @RequestBody RestockRequest request) {
		
		return ResponseEntity.ok(productService.restockProduct(id, request.getQuantity()));
	}
	
	@GetMapping("/low-stock")
	public ResponseEntity<List<ProductResponse>> getLowStockProducts(
				@RequestParam(defaultValue = "10") Integer threshold) {
		
		return ResponseEntity.ok(productService.getLowStockProducts(threshold));
	}
}
