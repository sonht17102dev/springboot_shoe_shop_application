package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.Comment;
import com.sonht.e_commerce_webapp_spring_boot.repository.CommentRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    /*
     * Lấy danh sách bình luận của một sản phẩm theo productId, sắp xếp theo thời gian tạo mới nhất (giảm dần)
     */
    @Override
    public List<Comment> findByProductIdOrderByCreatedAtDesc(Long productId) {
        return commentRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
    /*
     * Lưu bình luận vào cơ sở dữ liệu
     */
    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
    
}
