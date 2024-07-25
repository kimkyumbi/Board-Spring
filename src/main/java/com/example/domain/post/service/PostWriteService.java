package com.example.domain.post.service;

import com.example.domain.post.dto.request.PostRequestDTO;

public interface PostWriteService {
    void execute(PostRequestDTO postRequestDTO);
}
