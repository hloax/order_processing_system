package com.orderprocessing.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderprocessing.dto.product.ProductRequest;
import com.orderprocessing.dto.product.ProductResponse;
import com.orderprocessing.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		
		this.productService = productService;
	}
	
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
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
				@PathVariable Long id, @RequestBody ProductRequest request) {
		
		return ResponseEntity.ok(productService.updateProduct(id, request));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		
		productService.deleteProduct(id);
		
		return ResponseEntity.ok("Product deleted successfully");
	}
	
}
