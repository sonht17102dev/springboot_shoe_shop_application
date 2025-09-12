package com.sonht.e_commerce_webapp_spring_boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findTopByProductIdOrderByCreatedAtDesc(Long productId);
}
