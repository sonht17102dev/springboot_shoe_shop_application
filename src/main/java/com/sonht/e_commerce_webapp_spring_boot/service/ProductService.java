package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;


public interface ProductService {

    List<Product> findAllByIsDelete(boolean isDelete);

    void createProduct(ProductDto newProduct);

    void updateStatusProduct(Long productId);

    void deleteProductById(Long productId);
    
    Product findById(Long productId);

    void updateProduct(ProductDto currentProduct);

    List<Product> searchProducts(String keyword);

    List<Product> findAllByStatus(String status);

    List<Product> filterProducts(Long categoryId, Long brandId, Long priceMin, Long priceMax, Integer size, Long colorId);
}
