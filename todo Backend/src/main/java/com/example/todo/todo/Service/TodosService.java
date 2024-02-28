package com.example.todo.todo.Service;

import com.example.todo.todo.Model.Todos;
import com.example.todo.todo.Repository.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodosService {
    @Autowired
    private TodosRepository repository;

    public Todos saveTodos(Todos todo) {
        return repository.save(todo);
    }

    public List<Todos> getAllTodos() {
        return repository.findAll();
    }

    public Optional<Todos> getTodosById(Long todo_id) {
        return repository.findById(todo_id);
    }

    public Todos updateTodos(Long todo_id, Todos todo){
        if(repository.existsById(todo_id)){
            todo.setTodo_id(todo_id);
            return repository.save(todo);
        }
        return null;
    }

    public void deleteTodos(Long todo_id){
        repository.deleteById(todo_id);
    }
}
