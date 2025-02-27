package com.example.Sofia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDateTime createdAt;

    public Post() {}

    public Post(String content, User author) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }
}
