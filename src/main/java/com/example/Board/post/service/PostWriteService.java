package com.example.Board.post.service;

import com.example.Board.post.dto.request.PostRequestDTO;

public interface PostWriteService {
    void execute(PostRequestDTO postRequestDTO);
}
