package com.example.Sofia.repository;

import com.example.Sofia.model.Post;
import com.example.Sofia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


//Тут реализована логика, выборка постов пользователя с сортировкой по дате.
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorOrderByCreatedAtDesc(User author);
}
