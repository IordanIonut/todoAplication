package com.example.todo.todo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "todos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Todos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todo_id;
    @NotNull(message = "user_id")
    @ManyToOne
    private Users user_id;
    @NotNull(message = "Date")
    private LocalDateTime date;
}
