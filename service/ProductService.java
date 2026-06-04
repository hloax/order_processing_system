package com.orderprocessing.service;

import java.util.List;

import com.orderprocessing.dto.product.*;

public interface ProductService {

	ProductResponse createProduct(ProductRequest request);
	List<ProductResponse> getAllProducts();
	ProductResponse getProductById(Long id);
	ProductResponse updateProduct(Long id, ProductRequest request);
	void deleteProduct(Long id);
}
