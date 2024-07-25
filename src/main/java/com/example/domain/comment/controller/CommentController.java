package com.example.domain.comment.controller;

import com.example.domain.comment.dto.request.CommentRequest;
import com.example.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{post_id}")
    /**
     * 반환 타입 - ResponseEntity<CommentRequest>
     * @PathVariable로 postId를 받음
     * @RequestBody로 CommentRequest(postId, text) 값을 받음
     *
     * @param postId
     * @param CommentRequest
     */
    public ResponseEntity<CommentRequest> comment (
            @PathVariable(name = "post_id") Long postId,
            @RequestBody CommentRequest commentRequest
    ) {
        // commentService의 execute 메서드 호출
        commentService.execute(postId, commentRequest);
        // 상태 코드 200 ok와 함께 ResponseEntity 반환
        return ResponseEntity.ok().build();
    }
}
