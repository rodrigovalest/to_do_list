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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/tasks")
public class TasksController {
    @Autowired
    private TaskRepository taskRepository;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<?> getAll()  throws Exception {
        List<Task> taskList = taskRepository.findAll();
        HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.ok()
                .headers(headers)
                .body(taskList);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable UUID id
    ) throws Exception {
        if (!taskRepository.existsById(id))
            return ResponseEntity.badRequest().body("Inexistent task");

        return ResponseEntity.ok().body(taskRepository.findById(id));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<?> save(
            @Valid @RequestBody TaskDTO taskDTO,
            BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body("Invalid body");

        return ResponseEntity.ok().body(taskRepository.save(taskDTO.toTask()));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @Valid @RequestBody TaskDTO taskDTO,
            BindingResult bindingResult
    ) throws Exception {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body("Invalid body");

        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty())
            return ResponseEntity.badRequest().body("Inexistent task");

        Task task = optionalTask.get();
        taskDTO.updateTask(task);

        return ResponseEntity.ok().body(taskRepository.save(task));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(
            @PathVariable UUID id
    ) throws Exception {
        if (!taskRepository.existsById(id))
            return ResponseEntity.badRequest().body("Inexistent task");

        taskRepository.deleteById(id);

        return ResponseEntity.ok().body("Sucess on deleting");
    }
}
