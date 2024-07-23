package com.example.domain.post.service.Impl;

import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.post.service.PatchPostService;
import com.example.domain.post.dto.request.PatchPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatchPostServiceImpl implements PatchPostService {
    private final PostRepository postRepository;

    /**
     * 게시글을 수정하는 비즈니스 로직
     *
     * @param patchPostRequest
     * @param postId
     */
    @Override
    @Transactional
    public void execute(PatchPostRequest patchPostRequest, Long postId) {
        // PostEntity 객체를 생성해 DB에서 postId로 게시글을 찾아 저장
        // 오류가 생겼을 경우 RuntimeException 던지기
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        // postEntity에 저장된 게시글을 PostEntity의 updatePost 메서드로 수정
        postEntity.updatePost(patchPostRequest);
        // DB에 수정된 내용을 저장
        postRepository.save(postEntity);
    }
}
