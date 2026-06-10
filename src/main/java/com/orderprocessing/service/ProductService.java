package com.orderprocessing.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.orderprocessing.dto.product.*;

public interface ProductService {

	ProductResponse createProduct(ProductRequest request);
	List<ProductResponse> getAllProducts();
	ProductResponse getProductById(Long id);
	ProductResponse updateProduct(Long id, ProductRequest request);
	void deleteProduct(Long id);
	Page<ProductResponse> getProducts(int page, int size, String sortBy);
	ProductResponse restockProduct(Long productId, Integer quantity);
	List<ProductResponse> getLowStockProducts(Integer threshold);
}
