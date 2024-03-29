package com.example.todo.todo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Data
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tag_id;
    @NotNull(message = "Todo")
    @ManyToOne
    private Todos todo_id;
    @NotNull(message = "Name")
    private String name;
}
