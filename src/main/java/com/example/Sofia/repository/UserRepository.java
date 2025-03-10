package com.example.Sofia.repository;

import com.example.Sofia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//UserRepository — интерфейс для работы с таблицей (сохранение, поиск, удаление пользователей).
//  Ищет пользователей по имени и друзей.
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    @Query("SELECT f.receiver FROM Friendship f WHERE f.sender.id = :userId AND f.status = 'ACCEPTED'")
    List<User> findFriendsByUser(@Param("userId") Long userId);
}
