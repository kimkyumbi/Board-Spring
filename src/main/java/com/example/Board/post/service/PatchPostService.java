package com.example.Board.post.service;

import com.example.Board.post.dto.request.PatchPostRequest;

public interface PatchPostService {
    void execute(PatchPostRequest patchPostRequest, Long id);
}
