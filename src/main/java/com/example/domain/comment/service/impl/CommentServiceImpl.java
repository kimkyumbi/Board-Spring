package com.example.domain.comment.service.impl;

import com.example.domain.comment.repository.CommentRepository;
import com.example.domain.comment.service.CommentService;
import com.example.domain.comment.dto.request.CommentRequest;
import com.example.domain.comment.entity.CommentEntity;
import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void execute(CommentRequest commentPost, Long postId) {
        PostEntity post = postRepository.getReferenceById(postId);

        CommentEntity comment = CommentEntity.builder()
                .post(post)
                .text(commentPost.getText())
                .build();

        commentRepository.save(comment);
    }
}
