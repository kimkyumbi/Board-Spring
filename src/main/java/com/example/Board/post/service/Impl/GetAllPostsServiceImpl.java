package com.example.Board.post.service.Impl;

import com.example.Board.post.entity.PostEntity;
import com.example.Board.post.repository.PostRepository;
import com.example.Board.post.service.GetAllPostsService;
import com.example.Board.post.dto.response.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllPostsServiceImpl implements GetAllPostsService {
    private final PostRepository postRepository;

    public List<PostDTO> getAllPost() {
        List<PostEntity> postEntities = postRepository.findAll();

        return postEntities.stream().map(postEntity -> new PostDTO(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getVisit()
        )).toList();
    }
}
