package com.example.Sofia.controller;

import com.example.Sofia.model.Post;
import com.example.Sofia.model.User;
import com.example.Sofia.repository.PostRepository;
import com.example.Sofia.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/posts")

public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public String createPost(@RequestParam String content, @AuthenticationPrincipal UserDetails userDetails) {
        User author = userRepository.findByUsername(userDetails.getUsername());
        Post post = new Post(content, author);
        postRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping
    public String getPosts(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Post> posts = postRepository.findByAuthorOrderByCreatedAtDesc(user);
        model.addAttribute("posts", posts);
        return "posts";
    }
}
