package com.example.Board.post.service;


import com.example.Board.post.dto.response.GetPostResponse;

public interface GetPostByIdService {
    GetPostResponse execute(Long id);
}
