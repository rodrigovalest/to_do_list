package com.todolist.controllers;

import com.todolist.config.security.JwtTokenService;
import com.todolist.models.task.Task;
import com.todolist.models.task.TaskDTO;
import com.todolist.models.task.TaskResponse;
import com.todolist.models.user.User;
import com.todolist.repositories.TaskRepository;
import com.todolist.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/tasks")
public class TasksController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private JwtTokenService jwtTokenService;


    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader(value = "Authorization", required = false) String token
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();
        User user = jwtTokenService.getUserByToken(token);

        List<Task> taskList = taskRepository.findByUserId(user.getId());
        List<TaskResponse> taskResponseList = new ArrayList<>();

        for (Task task : taskList) taskResponseList.add(new TaskResponse(task));

        response.put("data", taskResponseList);
        response.put("message", "Success on get all");
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{taskId}")
    public ResponseEntity<?> getOne(
            @PathVariable UUID taskId,
            @RequestHeader(value = "Authorization", required = false) String token
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();
        User user = jwtTokenService.getUserByToken(token);

        if (!taskRepository.existsByIdAndUser(taskId, user)) {
            response.put("message", "Inexistent task");
            return ResponseEntity.badRequest().body(response);
        }

        Task task = taskRepository.findByIdAndUserId(taskId, user.getId());

        response.put("data", new TaskResponse(task));
        response.put("message", "Sucess on get one");
        return ResponseEntity.ok().body(response);
    }


    @PostMapping
    public ResponseEntity<?> save(
            @RequestHeader(value = "Authorization", required = false) String token,
            @Valid @RequestBody TaskDTO taskDTO,
            BindingResult bindingResult
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();
        User user = jwtTokenService.getUserByToken(token);

        if (bindingResult.hasErrors()) {
            response.put("message", "Invalid request body");
            return ResponseEntity.badRequest().body(response);
        }

        Task task = taskDTO.toTask();
        task.setUser(user);

        response.put("data", new TaskResponse(taskRepository.save(task)));
        response.put("message", "Sucess on save");
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<?> update(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable UUID taskId,
            @Valid @RequestBody TaskDTO taskDTO,
            BindingResult bindingResult
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();
        User user = jwtTokenService.getUserByToken(token);

        if (bindingResult.hasErrors()) {
            response.put("message", "Invalid request body");
            return ResponseEntity.badRequest().body(response);
        }
        if (!taskRepository.existsByIdAndUser(taskId, user)) {
            response.put("message", "Inexistent task");
            return ResponseEntity.badRequest().body(response);
        }

        Task task = taskRepository.findByIdAndUserId(taskId, user.getId());
        task.setText(taskDTO.getText());
        task.setStatus(taskDTO.getStatus());

        response.put("data", new TaskResponse(taskRepository.save(task)));
        response.put("message", "Sucess on update");
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> destroy(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable UUID taskId
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();
        User user = jwtTokenService.getUserByToken(token);

        if (!taskRepository.existsByIdAndUser(taskId, user)) {
            response.put("message", "Inexistent task");
            return ResponseEntity.badRequest().body(response);
        }

        taskRepository.deleteById(taskId);
        response.put("message", "Success on deleting");
        return ResponseEntity.ok().body(response);
    }
}
