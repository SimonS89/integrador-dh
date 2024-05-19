package com.example.workoutWonderland.controller;

import com.example.workoutWonderland.entity.Category;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.service.ICategoriyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoriyService categoriyService;

    public CategoryController(ICategoriyService categoriyService) {
        this.categoriyService = categoriyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) throws AlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriyService.create(category));
    }

    @GetMapping()
    public ResponseEntity<Set<Category>> listCategories() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(categoriyService.findAll());
    }

    @PutMapping("/modify")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(categoriyService.modify(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> categoryById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(categoriyService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws ResourceNotFoundException {
        categoriyService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category deleted successfully");
    }
}
