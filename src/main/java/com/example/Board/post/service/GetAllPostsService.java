package com.example.Board.post.service;

import com.example.Board.post.dto.response.PostDTO;

import java.util.List;

public interface GetAllPostsService {
    List<PostDTO> getAllPost();
}
