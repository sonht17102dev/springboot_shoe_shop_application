package com.sonht.e_commerce_webapp_spring_boot.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductSize;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {

   Optional<ProductSize> findByProductAndSize(Product product, Integer size);
}
