package com.example.Board.post.entity;

import com.example.Board.comment.entity.CommentEntity;
import com.example.Board.post.dto.request.PatchPostRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
    private int visit;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<CommentEntity> comment;

    public void updatePost (PatchPostRequest update) {
        this.title = update.getTitle();
        this.content = update.getContent();
    }
}
