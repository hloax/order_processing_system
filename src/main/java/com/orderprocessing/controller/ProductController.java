package com.orderprocessing.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderprocessing.dto.product.*;
import com.orderprocessing.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(
		name = "Products",
		description = "Operations for creating and managing products listed for sale.")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		
		this.productService = productService;
	}
	
	@Operation(
			summary = "Create a new product",
			description = "Create a product to sell"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Product created successfully")
	})
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(
			@Valid @RequestBody ProductRequest request) {
		return ResponseEntity.ok(productService.createProduct(request));
	}
	
	@Operation(
			summary = "List products",
			description = "List all products available for sale"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Products listed successfully")
	})
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@Operation(
			summary = "Show a product",
			description = "View a specific product available for sale"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Product listed successfully")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@Operation(
			summary = "Update product",
			description = "Edit details of a product available for sale"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Order created successfully"),
		@ApiResponse(responseCode = "403", description = "Admin role required"),
	})
	@PutMapping("{id}")
	public ResponseEntity<ProductResponse> updateProduct(
				@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
		
		return ResponseEntity.ok(productService.updateProduct(id, request));
	}
	
	@Operation(
			summary = "Delete product",
			description = "Remove a product from items for sale"
	)
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
	
	@Operation(
			summary = "Restock product",
			description = "Modify product quantity"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Quantity modified successfully")
	})
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
