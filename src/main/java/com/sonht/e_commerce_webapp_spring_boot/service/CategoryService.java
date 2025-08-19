package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.entity.Category;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;

public interface CategoryService {
    List<Category> findAll();
}
