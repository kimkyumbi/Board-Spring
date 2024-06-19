package com.example.Board.comment.service;

import com.example.Board.comment.dto.request.CommentRequest;

public interface CommentService {
    void execute(CommentRequest commentRequest, Long postId);
}
