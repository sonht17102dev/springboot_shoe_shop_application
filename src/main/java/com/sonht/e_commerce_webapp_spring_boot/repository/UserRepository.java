package com.sonht.e_commerce_webapp_spring_boot.repository;

import com.sonht.e_commerce_webapp_spring_boot.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByName(String consignee);
}
