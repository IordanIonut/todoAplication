package com.example.todo.todo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@Table(name = "lists")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long list_id;
    @NotNull(message = "Todo")
    @ManyToOne
    private Todos todo_id;
    @NotNull(message = "Title")
    private String title;
    @NotNull(message = "Priority")
    private String priority;
    @NotNull(message = "Status")
    private String status;
}
