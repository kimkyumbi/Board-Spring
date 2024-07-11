package com.example.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.domain.post.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
