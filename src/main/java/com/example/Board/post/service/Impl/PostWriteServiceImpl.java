package com.example.Board.post.service.Impl;

import com.example.Board.post.entity.PostEntity;
import com.example.Board.post.repository.PostRepository;
import com.example.Board.post.service.PostWriteService;
import com.example.Board.post.dto.request.PostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteServiceImpl implements PostWriteService {
    private final PostRepository postRepository;

    public void execute(PostRequestDTO postRequestDTO) {
        PostEntity postEntity = PostEntity.builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .build();

        postRepository.save(postEntity);
    }
}
