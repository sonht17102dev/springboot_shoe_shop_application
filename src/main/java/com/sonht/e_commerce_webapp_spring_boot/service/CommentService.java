package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.entity.Comment;

public interface CommentService {
    List<Comment> findByLastCreatedAt(Long productId);

    void saveComment(Comment comment);
}
