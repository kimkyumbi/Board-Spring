package com.example.Board.comment.controller;

import com.example.Board.comment.dto.request.CommentRequest;
import com.example.Board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}")
    public ResponseEntity<CommentRequest> comment (
            @PathVariable(name = "id") Long id,
            @RequestBody CommentRequest commentRequest
    ) {
        commentService.execute(commentRequest, id);
        return ResponseEntity.ok().build();
    }
}
