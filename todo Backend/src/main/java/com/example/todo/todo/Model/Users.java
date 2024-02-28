package com.example.todo.todo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @NotNull(message = "Name")
    private String name;
    @NotNull(message = "Email")
    private String email;
    @NotNull(message = "Password")
    private String password;
}
