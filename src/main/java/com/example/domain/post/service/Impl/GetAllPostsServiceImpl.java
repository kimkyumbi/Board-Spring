package com.example.domain.post.service.Impl;

import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.post.service.GetAllPostsService;
import com.example.domain.post.dto.response.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllPostsServiceImpl implements GetAllPostsService {
    private final PostRepository postRepository;

    /**
     * 게시글 리스트를 반환하는 비즈니스 로직
     */
    @Override
    @Transactional
    public List<PostDTO> getAllPost() {
        // PostEntity 객체를 List로 생성하고 postRepository에서 모든 게시물을 가져옴
        List<PostEntity> postEntities = postRepository.findAll();

        // postEntities 객체를 스트림으로 변환하고 람다식을 이용해 PostDTO 객체로 매핑하고 리스트로 변환
        return postEntities.stream().map(postEntity -> new PostDTO(
                postEntity.getId(),    // 게시물 아이디
                postEntity.getTitle(), // 게시물 제목
                postEntity.getCreatedAt(), // 게시물 작성 시간
                postEntity.getVisit()  // 게시물 조회수
        )).toList();
    }
}
