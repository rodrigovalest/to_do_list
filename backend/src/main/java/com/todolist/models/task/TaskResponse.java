package com.todolist.models.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private UUID id;
    private TaskStatus status;
    private String text;
    private UUID userId;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.status = task.getStatus();
        this.text = task.getText();
        this.userId = task.getUser().getId();
    }
}
