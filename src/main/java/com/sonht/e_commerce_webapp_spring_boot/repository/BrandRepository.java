package com.sonht.e_commerce_webapp_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name);
    
}
