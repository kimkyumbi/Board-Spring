package com.example.Board.comment.service.impl;

import com.example.Board.comment.repository.CommentRepository;
import com.example.Board.comment.service.CommentService;
import com.example.Board.comment.dto.request.CommentRequest;
import com.example.Board.comment.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public void execute(CommentRequest commentRequest, Long postId) {
        CommentEntity comment = CommentEntity.builder()
                .text(commentRequest.getText())
                .build();

        commentRepository.save(comment);
    }
}
