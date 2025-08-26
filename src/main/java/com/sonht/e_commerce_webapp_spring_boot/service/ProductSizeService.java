package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;
import java.util.Optional;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductSize;

public interface ProductSizeService {

    List<ProductSize> findAll();

    Optional<ProductSize> findProductSizeByProductAndSize(Product product, Integer size);

}
