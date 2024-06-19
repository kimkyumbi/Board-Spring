package com.example.Board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Board.post.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
