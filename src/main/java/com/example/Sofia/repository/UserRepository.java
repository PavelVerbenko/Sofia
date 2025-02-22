package com.example.Sofia.repository;

import com.example.Sofia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//UserRepository — интерфейс для работы с таблицей (сохранение, поиск, удаление пользователей). Spring автоматически создаст его реализацию.
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
