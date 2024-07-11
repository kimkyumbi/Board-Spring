package com.example.domain.comment.service.impl;

import com.example.domain.comment.repository.CommentRepository;
import com.example.domain.comment.service.CommentService;
import com.example.domain.comment.dto.request.CommentRequest;
import com.example.domain.comment.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public void execute(CommentRequest commentPost, Long postId) {
        CommentEntity comment = CommentEntity.builder()
                .text(commentPost.getText())
                .build();

        commentRepository.save(comment);
    }
}
