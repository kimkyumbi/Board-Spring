package com.example.domain.post.entity;

import com.example.domain.comment.entity.CommentEntity;
import com.example.domain.post.dto.request.PatchPostRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 게시글 엔티티
 */
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

    /**
     * 일대다 연관관계 매핑
     * 연관관계의 주인 : post
     * cascade 방식 채택
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;

    // 게시글 업데이트 메서드
    public void updatePost (PatchPostRequest update) {
        this.title = update.getTitle();
        this.content = update.getContent();
    }

    // 조회 수 증가 메서드
    public void visits () {
        this.visit = visit + 1;
    }
}
