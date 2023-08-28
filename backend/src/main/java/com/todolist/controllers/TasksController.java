package com.todolist.controllers;

import com.todolist.models.task.Task;
import com.todolist.models.task.TaskDTO;
import com.todolist.repositories.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/tasks")
public class TasksController {
    @Autowired
    private TaskRepository taskRepository;
    private final Map<String, Object> response = new HashMap<>();


    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        response.put("data", taskRepository.findAll());
        response.put("message", "Success on get all");
        return ResponseEntity.ok().body(response);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable UUID id
    ) throws Exception {
        if (!taskRepository.existsById(id)) {
            response.put("data", "");
            response.put("message", "Inexistent task");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("data", taskRepository.findById(id).get());
        response.put("message", "Sucess on get one");
        return ResponseEntity.ok().body(response);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<?> save(
            @Valid @RequestBody TaskDTO taskDTO,
            BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            response.put("data", "");
            response.put("message", "Invalid request body");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("data", taskRepository.save(taskDTO.toTask()));
        response.put("message", "Sucess on save");
        return ResponseEntity.ok().body(response);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @Valid @RequestBody TaskDTO taskDTO,
            BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors()) {
            response.put("data", "");
            response.put("message", "Invalid request body");
            return ResponseEntity.badRequest().body(response);
        }
        if (!taskRepository.existsById(id)) {
            response.put("data", "");
            response.put("message", "Inexistent task");
            return ResponseEntity.badRequest().body(response);
        }

        Task task = taskRepository.findById(id).get();
        taskDTO.updateTask(task);

        response.put("data", taskRepository.save(task));
        response.put("message", "Sucess on update");
        return ResponseEntity.ok().body(response);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(
            @PathVariable UUID id
    ) throws Exception {
        response.put("data", "");
        if (!taskRepository.existsById(id)) {
            response.put("message", "Inexistent task");
            return ResponseEntity.badRequest().body(response);
        }

        taskRepository.deleteById(id);
        response.put("message", "Success on deleting");
        return ResponseEntity.ok().body(response);
    }
}
