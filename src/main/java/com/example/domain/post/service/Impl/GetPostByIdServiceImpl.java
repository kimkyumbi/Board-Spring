package com.example.domain.post.service.Impl;

import com.example.domain.comment.dto.response.CommentResponse;
import com.example.domain.post.entity.PostEntity;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.post.service.GetPostByIdService;
import com.example.domain.post.dto.response.GetPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetPostByIdServiceImpl implements GetPostByIdService {
    private final PostRepository postRepository;

    /**
     * 주어진 id로 특정 게시글을 반환하는 비즈니스 로직
     *
     * @param postId
     */
    @Override
    @Transactional
    public GetPostResponse execute(Long postId) {
        // postRepository에서 주어진 id로 게시물을 찾아 postEntity 객체에 저장 / 존재하지 않을 경우 RuntimeException을 던짐
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(RuntimeException::new);

        // id에 해당하는 게시물의 조회수를 visit 메서드로 1 늘림
        postEntity.visits();

        // 변경된 postEntity 객체를 postRepository에 저장
        postRepository.save(postEntity);

        // builder 패턴을 이용해 GetPostResponse 객체를 생성해 게시물의 제목, 내용, 조회수, 댓글을 가져옴
        return GetPostResponse.builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .visit(postEntity.getVisit())
                .comments(postEntity.getComments().stream().map(
                        commentEntity -> new CommentResponse(
                                commentEntity.getId(),
                                commentEntity.getText()
                        )
                ).toList())
                .build();
    }
}
