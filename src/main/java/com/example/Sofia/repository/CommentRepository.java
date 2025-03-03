package com.example.Sofia.repository;

import com.example.Sofia.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
//выборка комментариев поста с сортировкой.
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
}
