package com.example.todo.todo.Repository;

import com.example.todo.todo.Model.Todos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosRepository extends JpaRepository<Todos, Long> {
}