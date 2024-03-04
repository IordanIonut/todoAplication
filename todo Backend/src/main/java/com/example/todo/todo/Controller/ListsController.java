package com.example.todo.todo.Controller;

import com.example.todo.todo.Model.Lists;
import com.example.todo.todo.Service.ListsService;
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
@RequestMapping("/api/list")
public class ListsController {
    @Autowired
    private ListsService service;

    @PostMapping("/save")
    public ResponseEntity<Object> saveLists(@Valid @RequestBody Lists lists, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveLists(lists), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Lists>> getAllLists() {
        return ResponseEntity.ok(service.getAllLists());
    }

    @GetMapping("/id")
    public ResponseEntity<List<Lists>> getListsById(@RequestParam("list_id") Long list_id) {
        return service.getListsById(list_id).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteLists(@RequestParam("list_id") Long list_id){
        service.deleteLists(list_id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<Lists> updateLists(@RequestParam("list_id") Long list_id, Lists lists){
        Lists l = service.updateLists(list_id, lists);
        return l != null ? ResponseEntity.ok(l) : ResponseEntity.notFound().build();
    }
}
