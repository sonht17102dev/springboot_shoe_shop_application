package com.sonht.e_commerce_webapp_spring_boot.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sonht.e_commerce_webapp_spring_boot.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    /*
     * Lấy danh sách category theo tên category
     */
    List<Category> findByName(String category);

}
