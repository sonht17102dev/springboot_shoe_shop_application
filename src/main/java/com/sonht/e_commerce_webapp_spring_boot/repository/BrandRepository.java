package com.sonht.e_commerce_webapp_spring_boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    /*
     * Lấy danh sách brand theo tên brand
     */
    List<Brand> findByName(String name);

    /*
     * Lấy tất cả các brand
     */
    List<Brand> findAll();
}
