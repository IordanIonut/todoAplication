package com.example.todo.todo.Controller;

import com.example.todo.todo.Model.Comments;
import com.example.todo.todo.Model.Lists;
import com.example.todo.todo.Service.CommentsService;
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

@RestController
@CrossOrigin
@RequestMapping("/api/comment")
public class CommentsController {
    @Autowired
    private CommentsService service;

    @PostMapping("/save")
    public ResponseEntity<Object> saveComments(@Valid @RequestBody Comments comments, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveComments(comments), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comments>> getAllComments() {
        return ResponseEntity.ok(service.getAllComments());
    }

    @GetMapping("/id")
    public ResponseEntity<List<Comments>> getCommentsById(@RequestParam("comment_id") Long comment_id) {
        return service.getCommentsById(comment_id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteComments(@RequestParam("comment_id") Long comment_id) {
        service.deleteComments(comment_id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<Comments> updateComments(@RequestParam("comment_id") Long comment_id, Comments comments){
        Comments c = service.updateComments(comment_id, comments);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }
}
