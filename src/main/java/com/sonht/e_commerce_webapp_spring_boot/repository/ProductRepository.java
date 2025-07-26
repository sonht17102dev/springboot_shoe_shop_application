package com.sonht.e_commerce_webapp_spring_boot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByIsDelete(boolean isDelete);
    boolean existsById(Long id);
}
