package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.Brand;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.repository.BrandRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    
    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public List<Brand> findByName(String brandName) {
        return brandRepository.findByName(brandName);
    }

    @Override
    public List<Product> findProductsByBrandId(Long brandId) {
        return brandRepository.findById(brandId)
                .map(Brand::getProducts)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + brandId));
                
    }

    
}
