package com.example.domain.comment.entity;

import com.example.domain.post.entity.PostEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 엔티티
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    // 다대일 연관관계 매핑
    @ManyToOne
    // post 엔티티의 id와 조인
    @JoinColumn(name = "post_id")
    private PostEntity post;
}
