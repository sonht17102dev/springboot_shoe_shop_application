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
    /*
     * Lấy tất cả Brand trong hệ thống
     */
    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    /*
     * Lấy Brand dựa trên brandName
     */
    @Override
    public List<Brand> findByName(String brandName) {
        return brandRepository.findByName(brandName);
    }

    /*
     * Lấy danh sách sản phẩm theo brandId
     */
    @Override
    public List<Product> findProductsByBrandId(Long brandId) {
        return brandRepository.findById(brandId)
                .map(Brand::getProducts)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + brandId));
                
    }
    /*
     * Lấy danh sách sản phẩm theo brandName
     */
    @Override
    public List<Product> findProductsByBrandName(String brandName) {
        return brandRepository.findByName(brandName).stream()
                .flatMap(brand -> brand.getProducts().stream())
                .toList();
    }

    
}
