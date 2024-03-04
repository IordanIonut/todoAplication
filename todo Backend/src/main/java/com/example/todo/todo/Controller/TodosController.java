package com.example.todo.todo.Controller;

import com.example.todo.todo.Model.Todos;
import com.example.todo.todo.Model.Users;
import com.example.todo.todo.Service.TodosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/todo")
public class TodosController {
    @Autowired
    private TodosService service;

    @PostMapping("/save")
    public ResponseEntity<Object> saveTags(@Valid @RequestBody Todos todos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveTodos(todos), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Todos>> getAllTodos() {
        return ResponseEntity.ok(service.getAllTodos());
    }

    @GetMapping("/id")
    public ResponseEntity<List<Todos>> getTodosById(@RequestParam("todo_id") Long todo_id){
        return service.getTodosById(todo_id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteTodos(@RequestParam("todo_id") Long todo_id){
        service.deleteTodos(todo_id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<Todos> updateTodos(@RequestParam("todo_id") Long todo_id, @RequestBody Todos todos){
        Todos t = service.updateTodos(todo_id, todos);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }
}
