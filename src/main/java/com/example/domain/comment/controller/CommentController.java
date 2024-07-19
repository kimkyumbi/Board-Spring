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

    @PostMapping("/{post_id}")
    public ResponseEntity<CommentRequest> comment (
            @PathVariable(name = "post_id") Long postId,
            @RequestBody CommentRequest commentRequest
    ) {
        commentService.execute(commentRequest, postId);
        return ResponseEntity.ok().build();
    }
}
