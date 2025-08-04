package com.sonht.e_commerce_webapp_spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;

@Repository
public interface OrderRepository extends JpaRepository<OrderWeb, Long> {

    
} 
