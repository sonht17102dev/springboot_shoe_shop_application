package com.sonht.e_commerce_webapp_spring_boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /*
     * Lấy danh sách bình luận của một sản phẩm theo productId, sắp xếp theo thời gian tạo mới nhất (giảm dần)
     */
    List<Comment> findByProductIdOrderByCreatedAtDesc(Long productId);
}
