package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.entity.Brand;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;


public interface BrandService {
    List<Brand> findAll();

    List<Brand> findByName(String brandName);

    List<Product> findProductsByBrandId(Long brandId);

    List<Product> findProductsByBrandName(String brandName);
}
