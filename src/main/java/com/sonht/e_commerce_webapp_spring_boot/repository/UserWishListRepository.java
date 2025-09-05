package com.sonht.e_commerce_webapp_spring_boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonht.e_commerce_webapp_spring_boot.entity.UserWishlist;

public interface UserWishListRepository extends JpaRepository<UserWishlist, Long> {

    Optional<UserWishlist> findByCustomerIdAndProductId(Long customerId, Long productId);

    void deleteByCustomerIdAndProductId(Long customerId, Long productId);
}
