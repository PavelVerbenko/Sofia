package com.example.Sofia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

//класс для лайков
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Like() {}

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
