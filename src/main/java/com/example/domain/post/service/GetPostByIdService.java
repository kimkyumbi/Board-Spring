package com.example.domain.post.service;

import com.example.domain.post.dto.response.GetPostResponse;

public interface GetPostByIdService {
    GetPostResponse execute(Long postId);
}
