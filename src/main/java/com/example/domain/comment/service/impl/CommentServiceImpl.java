package com.example.domain.comment.service.impl;

import com.example.domain.comment.repository.CommentRepository;
import com.example.domain.comment.service.CommentService;
import com.example.domain.comment.dto.request.CommentRequest;
import com.example.domain.comment.entity.CommentEntity;
import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    /**
     * comment를 작성하는 비즈니스 로직
     *
     * @param postId
     * @param CommendRequest
     */
    public void execute(Long postId, CommentRequest commentPost) {
        // PostEntity 객체를 생성해 postId로 게시물 저장
        PostEntity post = postRepository.getReferenceById(postId);

        // CommentEntity 객체를 생성해 빌드 객체로 변환
        CommentEntity comment = CommentEntity.builder()
                .post(post) // 저장한 PostEntity 객체
                .text(commentPost.getText()) // CommentRequest에서 받은 Text를 저장
                .build();

        // DB에 저장
        commentRepository.save(comment);
    }
}
