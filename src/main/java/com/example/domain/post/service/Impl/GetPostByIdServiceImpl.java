package com.example.domain.post.service.Impl;

import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.post.service.GetPostByIdService;
import com.example.domain.post.dto.response.GetPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPostByIdServiceImpl implements GetPostByIdService {
    private final PostRepository postRepository;

    public GetPostResponse execute(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(RuntimeException::new);
        postEntity.visits();
        postRepository.save(postEntity);
        return GetPostResponse.builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .visit(postEntity.getVisit())
                .comments(postEntity.getComments())
                .build();
    }
}
