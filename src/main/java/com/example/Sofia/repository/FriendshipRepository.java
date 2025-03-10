package com.example.Sofia.repository;

import com.example.Sofia.model.Friendship;
import com.example.Sofia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
//Позволяет искать запросы по отправителю/получателю.
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findBySenderAndStatus (User sender, Friendship.Status status);
    List<Friendship> findByReceiverAndStatus(User receiver, Friendship.Status status);
}
