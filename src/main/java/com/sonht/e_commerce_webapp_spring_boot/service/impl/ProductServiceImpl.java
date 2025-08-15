package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductImage;
import com.sonht.e_commerce_webapp_spring_boot.repository.BrandRepository;
import com.sonht.e_commerce_webapp_spring_boot.repository.ProductRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }

   

    @Override
    public void createProduct(ProductDto newProduct) {
        Product newProductEntity = new Product();
        newProductEntity.setName(newProduct.getName());
        newProductEntity.setVersionName(newProduct.getVersionName());
        newProductEntity.setDescription(newProduct.getDescription());
        newProductEntity.setPrice(newProduct.getPrice());
        newProductEntity.setIsDelete(false);
        newProductEntity.setStatus(newProduct.getStatus());
        String brandName = newProduct.getBrandName();

        newProductEntity.setBrand(
            brandRepository.findByName(brandName).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Brand not found: " + brandName))
        );

        ProductImage newProductImage = new ProductImage();
        newProductImage.setImageUrl(newProduct.getImageUrl());
        newProductImage.setPrimary(true);
        newProductImage.setProduct(newProductEntity);
        newProductEntity.setProductImages(List.of(newProductImage));
        newProductEntity.setCreatedAt(new java.util.Date());
        newProductEntity.setUpdatedAt(new java.util.Date());
        productRepository.save(newProductEntity);
    }

    @Override
    public void updateStatusProduct(Long productId) {
        productRepository.findById(productId).ifPresent(product -> {
            if (product.getStatus().equals("Ngung ban")) {
                product.setStatus("Dang ban");
            } else if (product.getStatus().equals("Dang ban")) {
                product.setStatus("Ngung ban");
            }
            productRepository.save(product);
        });
    }

    @Override
    public List<Product> findAllByIsDelete(boolean isDelete) {
        return productRepository.findAllByIsDelete(false);
    }



    @Override
    @Transactional
    public Product findById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }
    @Override
    @Transactional
    public void deleteProductById(Long productId) {
        Product product = findById(productId);
        product.setIsDelete(true);
        productRepository.save(product);
    }



    @Override
    public void updateProduct(ProductDto currentProduct) {
        // 1. Lấy product từ DB
        Product product = findById(currentProduct.getId());

        // 2. Cập nhật các trường cần thiết
        product.setName(currentProduct.getName());
        product.setVersionName(currentProduct.getVersionName());
        product.setDescription(currentProduct.getDescription());
        product.setPrice(currentProduct.getPrice());
        product.setStatus(currentProduct.getStatus());
        
        product.setBrand(
            brandRepository.findByName(currentProduct.getBrandName()).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Brand not found: " + currentProduct.getBrandName()))
        );
        
        //  Set tất cả ảnh hiện có về isPrimary = false
        product.getProductImages().forEach(image -> image.setPrimary(false));
        //  Tạo ảnh mới và set isPrimary = true
        ProductImage newProductImage = new ProductImage();
        newProductImage.setImageUrl(currentProduct.getImageUrl());
        newProductImage.setPrimary(true);

        newProductImage.setProduct(product);

        product.getProductImages().add(newProductImage);

        productRepository.save(product);
    }

  

}
