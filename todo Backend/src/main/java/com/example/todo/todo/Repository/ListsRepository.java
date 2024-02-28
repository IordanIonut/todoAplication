package com.example.todo.todo.Repository;

import com.example.todo.todo.Model.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListsRepository extends JpaRepository<Lists, Long> {
}