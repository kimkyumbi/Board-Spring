package com.example.domain.post.service.Impl;

import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.post.service.DeletePostService;
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
