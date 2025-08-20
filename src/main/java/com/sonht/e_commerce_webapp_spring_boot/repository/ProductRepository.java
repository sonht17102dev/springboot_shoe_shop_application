package com.sonht.e_commerce_webapp_spring_boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByIsDelete(boolean isDelete);
    boolean existsById(Long id);

    // Không phân biệt hoa thường
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findAllByStatus(String status);

     @Query("SELECT p FROM Product p " +
           "JOIN p.category c " +
           "JOIN p.brand b " +
           "JOIN p.productColors pc " +
           "JOIN p.productSizes ps " +
           "WHERE (:categoryId IS NULL OR c.id = :categoryId) " +
           "AND (:brandId IS NULL OR b.id = :brandId) " +
           "AND (:priceMin IS NULL OR p.price >= :priceMin) " +
           "AND (:priceMax IS NULL OR p.price <= :priceMax) " +
           "AND (:size IS NULL OR ps.size = :size) " +
           "AND (:colorId IS NULL OR pc.color.id = :colorId)")
    List<Product> filterProducts(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("priceMin") Long priceMin,
            @Param("priceMax") Long priceMax,
            @Param("size") Integer size,
            @Param("colorId") Long colorId
    );
}
