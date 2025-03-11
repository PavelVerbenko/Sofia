package com.example.Sofia.repository;

import com.example.Sofia.model.Like;
import com.example.Sofia.model.Post;
import com.example.Sofia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//интерфейс для работы с лайками, проверка и подсчет лайков
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);
    List<Like> findByPost(Post post);
}
