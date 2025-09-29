package com.sonht.e_commerce_webapp_spring_boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    /*
     * Lấy tất cả sản phẩm chưa bị xóa (isDelete = false)
     */
    List<Product> findAllByIsDelete(boolean isDelete);

    /*
     * Kiểm tra sự tồn tại của một sản phẩm dựa trên ID
     */
    boolean existsById(Long id);

    /*
     * Tìm kiếm sản phẩm theo từ khóa trong tên sản phẩm, không phân biệt chữ hoa chữ thường
     */
    List<Product> findByNameContainingIgnoreCase(String keyword);

    /*
     * Lấy tất cả sản phẩm theo trạng thái (status)
     */
    List<Product> findAllByStatus(String status);

    /*
     * Lọc sản phẩm dựa trên nhiều tiêu chí: categoryId, brandId, priceMin, priceMax, size, colorId
     */
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
