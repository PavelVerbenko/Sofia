package com.example.Sofia.controller;

import com.example.Sofia.model.Friendship;
import com.example.Sofia.model.User;
import com.example.Sofia.repository.FriendshipRepository;
import com.example.Sofia.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/friends")
//Отправляет запросы, принимает их и отображает друзей.
public class FriendController {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendController(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/request")
    public String sendRequest (
            @RequestParam String username,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        User sender = userRepository.findByUsername(userDetails.getUsername());
        User receiver = userRepository.findByUsername(username);
        if (receiver == null) {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
            return "redirect:/friends";
        }

        Friendship request = new Friendship();
        request.setSender(sender);
        request.setReceiver(receiver);
        friendshipRepository.save(request);

        return "redirect:/friends";
    }

    @PostMapping("/{requestId}/accept")
    public String acceptRequest(@PathVariable Long requestId) {
        Friendship request = friendshipRepository.findById(requestId).orElseThrow();
        request.setStatus(Friendship.Status.ACCEPTED);
        friendshipRepository.save(request);
        return "redirect:/friends";
    }

    @GetMapping
    public String getFriends(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<Friendship> requests = friendshipRepository.findByReceiverAndStatus(user, Friendship.Status.PENDING);
        List<User> friends = userRepository.findFriendsByUser(user.getId());

        model.addAttribute("requests", requests);
        model.addAttribute("friends", friends);
        return "friends";
    }
}
