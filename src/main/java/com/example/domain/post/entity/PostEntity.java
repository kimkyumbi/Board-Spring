package com.example.domain.post.entity;

import com.example.domain.comment.entity.CommentEntity;
import com.example.domain.post.dto.request.PatchPostRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;

    public void updatePost (PatchPostRequest update) {
        this.title = update.getTitle();
        this.content = update.getContent();
    }

    public void visits () {
        this.visit = visit + 1;
    }
}
