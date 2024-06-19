package com.example.Board.post.service.Impl;

import com.example.Board.post.entity.PostEntity;
import com.example.Board.post.repository.PostRepository;
import com.example.Board.post.service.DeletePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostServiceImpl implements DeletePostService {
    private final PostRepository postRepository;

    public void deletePost(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(RuntimeException::new);
        postRepository.delete(postEntity);
    }
}
