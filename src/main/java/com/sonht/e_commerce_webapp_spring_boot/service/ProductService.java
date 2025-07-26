package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;

import jakarta.validation.Valid;


public interface ProductService {

    List<Product> findAllByIsDelete(boolean isDelete);

    void createProduct(ProductDto newProduct);

    void updateStatusProduct(Long productId);
    void deleteProductById(Long productId);
    Product findById(Long productId);

    void updateProduct(ProductDto currentProduct);
}
