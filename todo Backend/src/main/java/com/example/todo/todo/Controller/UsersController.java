package com.example.todo.todo.Controller;

import com.example.todo.todo.Model.Users;
import com.example.todo.todo.Service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService service;

    @PostMapping("/save")
    public ResponseEntity<Object> saveUsers(@Valid @RequestBody Users users, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveUsers(users), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = service.getAllUsers();
        System.out.println(users);
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/id")
    public ResponseEntity<List<Users>> getUsersById(@RequestParam("user_id") Long user_id) {
        return service.getUsersById(user_id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteUsers(@RequestParam("user_id") Long user_id) {
        service.deleteUsers(user_id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<Users> updareUsers(@RequestParam("user_id") Long user_id, @RequestBody Users users) {
        Users u = service.updateUsers(user_id, users);
        System.out.println(u);
        System.out.println(user_id);
        System.out.println(users);
        return u != null ? ResponseEntity.ok(u) : ResponseEntity.notFound().build();
    }
}
