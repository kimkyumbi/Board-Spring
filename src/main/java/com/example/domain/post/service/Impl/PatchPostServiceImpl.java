package com.example.domain.post.service.Impl;

import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.post.service.PatchPostService;
import com.example.domain.post.dto.request.PatchPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatchPostServiceImpl implements PatchPostService {
    private final PostRepository postRepository;

    public void execute(PatchPostRequest patchPostRequest, Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(RuntimeException::new);

        postEntity.updatePost(patchPostRequest);

        postRepository.save(postEntity);
    }
}
