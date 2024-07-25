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

    /**
     * 반환 타입 - ResponseEntity<Void>
     * @RequestBody로 PostRequestDTO를 받음
     * @Valid로 검증
     *
     * @param requestDTO
     */
    @PostMapping
    public ResponseEntity<Void> writePost(@RequestBody @Valid PostRequestDTO requestDTO) {
        // postWriteService의 execute 메서드 호출
        // execute 메서드가 파라미터로 받는 PostRequestDTO 값을 넣어줌
        postWriteService.execute(requestDTO);
        // status - CREATED / ResponseEntity 반환
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 반환 타입 - ResponseEntity<GetPostResponse>
     * @PathVariable로 게시글의 postId를 받음
     *
     * @param postId
     */
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPostById(@PathVariable("postId") Long postId) {
        // GetPostResponse 객체를 생성해서 getPostByIdService의 execute 메서드에 postId를 넣어 특정 게시글을 조회
        GetPostResponse getPostResponse = getPostByIdService.execute(postId);
        // 상태 코드 200 ok와 body로 getPostResponse를 주고 ResponseEntity 반환
        return ResponseEntity.ok(getPostResponse);
    }

    /**
     * 반환 타입 - ResponseEntity<List<PostDTO>>
     */
    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        // PostDTO 객체를 List로 만들어 getAllPostsService의 getAllPost 메서드를 호출해 모든 게시글을 조회
        List<PostDTO> postList = getAllPostsService.getAllPost();
        // 상태 코드 200 ok와 body로 postList를 주고 ResponseEntity 반환
        return ResponseEntity.ok(postList);
    }

    /**
     * 반환 타입 - ResponseEntity<Void>
     *
     * @param postId
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        // deletePostService에서 deletePost 메서드를 호출해서 postId를 넣어 게시글 삭제
        deletePostService.deletePost(postId);
        // 상태 코드 204 noContent를 주고 ResponseEntity 반환
        return ResponseEntity.noContent().build();
    }

    /**
     * 반환 타입 - ResponseEntity<Void>
     * @PathVariable로 postId를 받음
     * @RequestBody로 patchPostRequest를 받음
     *
     * @param postId
     * @param patchPostRequest
     */
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> patchPost(
            @PathVariable("postId") Long postId,
            @RequestBody PatchPostRequest patchPostRequest
    ) {
        // patchPostService의 execute 메서드를 호출해 patchPostRequest에서 변경된 값과 postId를 넣어 게시글 수정
        patchPostService.execute(patchPostRequest, postId);
        // 상태 코드 204 noContent를 주고 ResponseEntity 반환
        return ResponseEntity.noContent().build();
    }
}
