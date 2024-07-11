package com.example.domain.post.service;

import com.example.domain.post.dto.response.PostDTO;

import java.util.List;

public interface GetAllPostsService {
    List<PostDTO> getAllPost();
}
