package com.orderprocessing.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.orderprocessing.dto.product.ProductRequest;
import com.orderprocessing.dto.product.ProductResponse;
import com.orderprocessing.entity.Product;
import com.orderprocessing.exception.ProductNotFoundException;
import com.orderprocessing.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		
		this.productRepository = productRepository;
	}
	
	@Override
	public ProductResponse createProduct(ProductRequest request) {
		
		Product product = new Product();
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		product.setCategory(request.getCategory());
		
		Product savedProduct = productRepository.save(product);
		
		return mapToResponse(savedProduct);
	}
	
	@Override
	public List<ProductResponse> getAllProducts() {
		
		return productRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	@Override
	public ProductResponse getProductById(Long id) {
		
		Product product = 
				productRepository.findById(id)
				.orElseThrow(() ->
						new ProductNotFoundException(id));
		
		return mapToResponse(product);
	}
	
	@Override
	public ProductResponse updateProduct(Long id, ProductRequest request) {
		
		Product product =
				productRepository.findById(id)
				.orElseThrow(() ->
					new ProductNotFoundException(id));
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		product.setCategory(request.getCategory());
		
		Product updated =
				productRepository.save(product);
		
		return mapToResponse(updated);
	}
	
	@Override
	public void deleteProduct(Long id) {
		
		Product product =
				productRepository.findById(id)
				.orElseThrow(() ->
					new ProductNotFoundException(id));
		
		productRepository.delete(product);
	}
	
	@Override
	public Page<ProductResponse> getProducts(int page, int size, String sortBy) {
		
		Pageable pageable =
				PageRequest.of(page, size, Sort.by(sortBy));
		
		return productRepository
				.findAll(pageable)
				.map(this::mapToResponse);
	}

	@Override
	@Transactional
	public ProductResponse restockProduct(Long productId, Integer quantity) {
		
		Product product =
				productRepository.findById(productId)
				.orElseThrow(() ->
						new ProductNotFoundException(productId));
		
		product.setStockQuantity(product.getStockQuantity() + quantity);
		
		Product updated = productRepository.save(product);
		
		return mapToResponse(updated);
	}
	
	@Override
	public List<ProductResponse> getLowStockProducts(Integer threshold) {
		
		return productRepository
				.findByStockQuantityLessThanEqual(threshold)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}
	
	private ProductResponse mapToResponse(Product product) {
		
		return new ProductResponse(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getStockQuantity(),
				product.getCategory()
		);
	}
}
