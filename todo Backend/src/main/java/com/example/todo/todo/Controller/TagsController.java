package com.example.todo.todo.Controller;

import com.example.todo.todo.Model.Tags;
import com.example.todo.todo.Model.Todos;
import com.example.todo.todo.Service.TagsService;
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
@RequestMapping("/api/tags")
public class TagsController {
    @Autowired
    private TagsService service;

    @PostMapping("/save")
    public ResponseEntity<Object> saveTags(@Valid @RequestBody Tags tags, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveTags(tags), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tags>> getAllTags(){
        return ResponseEntity.ok(service.getAllTags());
    }

    @GetMapping("/id")
    public ResponseEntity<List<Tags>> getTagsById(@RequestParam("tag_id") Long tag_id){
        return service.getTagsById(tag_id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteTags(@RequestParam("tag_id") Long tag_id){
        service.deleteTags(tag_id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<Tags> updateTags(@RequestParam("tag_id") Long tag_id, @RequestBody Tags tags){
        Tags t = service.updateTags(tag_id, tags);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }
}
