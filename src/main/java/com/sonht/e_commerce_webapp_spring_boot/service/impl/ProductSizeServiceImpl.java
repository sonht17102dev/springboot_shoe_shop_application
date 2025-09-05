package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductSize;
import com.sonht.e_commerce_webapp_spring_boot.repository.ProductSizeRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    private final ProductSizeRepository productSizeRepository;
    
    public ProductSizeServiceImpl(ProductSizeRepository productSizeRepository) {
        this.productSizeRepository = productSizeRepository;
    }

    @Override
    public List<ProductSize> findAll() {
        return productSizeRepository.findAll();
    }

    @Override
    public Optional<ProductSize> findProductSizeByProductAndSize(Product product, Integer size) {
        
        return productSizeRepository.findByProductAndSize(product, size);
    }

    @Override
    public Optional<ProductSize> findById(Long id) {
        return productSizeRepository.findById(id);
    }

    
}
