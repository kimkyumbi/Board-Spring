package com.example.domain.post.controller;

import com.example.domain.post.dto.request.PatchPostRequest;
import com.example.domain.post.dto.request.PostRequestDTO;
import com.example.domain.post.dto.response.GetPostResponse;
import com.example.domain.post.dto.response.PostDTO;
import com.example.domain.post.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostWriteService postWriteService;
    private final GetAllPostsService getAllPostsService;
    private final DeletePostService deletePostService;
    private final PatchPostService patchPostService;
    private final GetPostByIdService getPostByIdService;

    @PostMapping
    public ResponseEntity<Void> writePost(@RequestBody @Valid PostRequestDTO requestDTO) {
        postWriteService.execute(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostResponse> getPostById(@PathVariable("id") Long id) {
        GetPostResponse getPostResponse = getPostByIdService.execute(id);
        return ResponseEntity.ok(getPostResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postList = getAllPostsService.getAllPost();
        return ResponseEntity.ok(postList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        deletePostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchPost(
            @PathVariable("id") Long id,
            @RequestBody PatchPostRequest patchPostRequest
    ) {
        patchPostService.execute(patchPostRequest, id);
        return ResponseEntity.noContent().build();
    }
}
