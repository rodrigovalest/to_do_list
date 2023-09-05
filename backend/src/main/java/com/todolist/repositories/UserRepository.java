package com.todolist.repositories;

import com.todolist.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM tb_users WHERE username = ?1", nativeQuery = true)
    User findUserByUsername(String username);
    UserDetails findByUsername(String username);
    Boolean existsByUsername(String username);
}
