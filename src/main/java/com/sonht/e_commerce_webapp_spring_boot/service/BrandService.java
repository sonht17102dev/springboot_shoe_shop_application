package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.entity.Brand;


public interface BrandService {
    List<Brand> findAll();

    List<Brand> findByName(String brandName);
}
