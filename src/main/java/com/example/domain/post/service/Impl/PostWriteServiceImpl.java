package com.example.domain.post.service.Impl;

import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.post.service.PostWriteService;
import com.example.domain.post.dto.request.PostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostWriteServiceImpl implements PostWriteService {
    private final PostRepository postRepository;

    /**
     * 게시글을 작성하는 비즈니스 로직
     *
     * @param postRequestDTO
     */
    @Override
    @Transactional
    public void execute(PostRequestDTO postRequestDTO) {
        // PostEntity 객체를 생성하고 빌더 객체로 변환
        PostEntity postEntity = PostEntity.builder()
                .title(postRequestDTO.getTitle()) // title에 postRequestDTO에서 받은 title 저장
                .content(postRequestDTO.getContent()) // content에 postRequestDTO에서 받은 content 저장
                .build();

        // DB에 저장
        postRepository.save(postEntity);
    }
}
