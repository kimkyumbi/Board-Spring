package com.example.domain.post.service.Impl;

import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.post.service.DeletePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePostServiceImpl implements DeletePostService {
    private final PostRepository postRepository;

    /**
     * 게시글의 id를 받아 삭제하는 메서드
     *
     * @param postId
     */
    @Override
    @Transactional
    public void deletePost(Long postId) {
        // postRepository에서 id를 찾아 PostEntity 객체에 저장 / 해당 게시물이 존재하지 않으면 RuntimeException을 던짐
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        // postReposiotry의 delete를 사용해 postEntity 객체에 저장된 값을 넣어 게시물 삭제
        postRepository.delete(postEntity);
    }
}
