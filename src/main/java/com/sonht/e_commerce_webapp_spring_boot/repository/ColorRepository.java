package com.sonht.e_commerce_webapp_spring_boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
    List<Color> findAll();

}
