package com.example.Sofia.controller;

import com.example.Sofia.model.Comment;
import com.example.Sofia.model.Like;
import com.example.Sofia.model.Post;
import com.example.Sofia.model.User;
import com.example.Sofia.repository.CommentRepository;
import com.example.Sofia.repository.LikeRepository;
import com.example.Sofia.repository.PostRepository;
import com.example.Sofia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/posts")
//Создает посты/комментарии и показывает ленту друзей.
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
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
        List<User> friends = userRepository.findFriendsByUser(user.getId());
        friends.add(user);

        posts.forEach(post -> {
            List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(post.getId());
            post.setComments(comments);
            post.setLikes(likeRepository.findByPost(post));
        });

        model.addAttribute("posts", posts);
        return "posts";


    }

    @PostMapping("/{postId}/comment")
    public String addComment(
            @PathVariable Long postId,
            @RequestParam String content,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User author = userRepository.findByUsername(userDetails.getUsername());
        Post post = postRepository.findById(postId).orElseThrow();
        Comment comment = new Comment(content, author, post);
        commentRepository.save(comment);
        return "redirect:/posts";
    }

    @PostMapping("/{postId}/like")
    public String likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        Post post = postRepository.findById(postId).orElseThrow();


        if (!likeRepository.existsByUserAndPost(user, post)) {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
        }
        return "redirect:/posts";
    }
}
