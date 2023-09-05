package com.todolist.models.task;

import com.todolist.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TB_TASKS")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime updateTimestamp;
}
