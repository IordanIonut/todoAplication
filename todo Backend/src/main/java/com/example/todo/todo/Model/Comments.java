package com.example.todo.todo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;
    @NotNull(message = "Todo")
    @ManyToOne
    private Todos todo_id;
    @NotNull(message = "User")
    @ManyToOne
    private Users user_id;
    @NotNull(message = "Text")
    private String text;
    @NotNull(message = "Created")
    private LocalDateTime created;
}
