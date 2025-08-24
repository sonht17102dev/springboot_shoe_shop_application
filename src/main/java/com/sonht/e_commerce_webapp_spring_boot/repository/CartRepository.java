package com.sonht.e_commerce_webapp_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Long>  {
    
}
