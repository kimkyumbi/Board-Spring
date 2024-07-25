package com.example.domain.comment.service;

import com.example.domain.comment.dto.request.CommentRequest;

public interface CommentService {
    void execute(Long postId, CommentRequest commentRequest);
}
