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
    @Override
    public List<Comment> findByLastCreatedAt(Long productId) {
        return commentRepository.findTopByProductIdOrderByCreatedAtDesc(productId);
    }
    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
    
}
