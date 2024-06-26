package com.example.Board.post.service.Impl;

import com.example.Board.post.entity.PostEntity;
import com.example.Board.post.repository.PostRepository;
import com.example.Board.post.service.PatchPostService;
import com.example.Board.post.dto.request.PatchPostRequest;
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
