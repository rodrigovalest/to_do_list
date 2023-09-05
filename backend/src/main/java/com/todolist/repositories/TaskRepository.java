package com.todolist.repositories;

import com.todolist.models.task.Task;
import com.todolist.models.task.TaskResponse;
import com.todolist.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUserId(UUID userId);
    Task findByIdAndUserId(UUID userId, UUID id);
    Boolean existsByIdAndUser(UUID id, User user);
}
