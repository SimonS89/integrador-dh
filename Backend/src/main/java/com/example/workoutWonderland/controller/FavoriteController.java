package com.example.workoutWonderland.controller;

import com.example.workoutWonderland.entity.Favorite;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.service.IFavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final IFavoriteService favoriteService;

    public FavoriteController(IFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Favorite> createFavorite(@RequestBody Favorite favorite) throws AlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.create(favorite));
    }

    @GetMapping()
    public ResponseEntity<Set<Favorite>> listFavorites() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(favoriteService.findAll());
    }

    @GetMapping("/by_user/{userId}")
    public ResponseEntity<List<Favorite>> listFavoritesByUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(favoriteService.listFavoritesByUser(userId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Favorite> favoriteById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(favoriteService.findById(id));
    }

    @PutMapping("/modify")
    public ResponseEntity<Favorite> updateFavorite(@RequestBody Favorite favorite) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(favoriteService.modify(favorite));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFavorite(@PathVariable Long id) throws ResourceNotFoundException {
        favoriteService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("deleted successfully");
    }
}
