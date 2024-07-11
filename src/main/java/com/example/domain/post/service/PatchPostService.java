package com.example.domain.post.service;

import com.example.domain.post.dto.request.PatchPostRequest;

public interface PatchPostService {
    void execute(PatchPostRequest patchPostRequest, Long id);
}
