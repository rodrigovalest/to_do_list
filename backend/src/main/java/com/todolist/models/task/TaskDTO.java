package com.todolist.models.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @NotNull
    @NotBlank
    private String text;

    @NotNull
    private TaskStatus status;

    public Task toTask() {
        Task task = new Task();
        task.setText(this.text);
        task.setStatus(this.status);
        return task;
    }

    public Task updateTask() {
        Task task = new Task();
        task.setText(this.text);
        task.setStatus(this.status);
        return task;
    }
}
