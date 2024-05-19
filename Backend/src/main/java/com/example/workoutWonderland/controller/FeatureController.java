package com.example.workoutWonderland.controller;

import com.example.workoutWonderland.entity.Feature;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.service.IFeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/feature")
public class FeatureController {
    private final IFeatureService featureService;

    public FeatureController(IFeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping("/create")
    public ResponseEntity<Feature> createFeature(@RequestBody Feature feature) throws AlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(featureService.create(feature));
    }

    @GetMapping()
    public ResponseEntity<Set<Feature>> listFeatures() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(featureService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feature> featureById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(featureService.findById(id));
    }

    @PutMapping("/modify")
    public ResponseEntity<Feature> updateFeature(@RequestBody Feature feature) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(featureService.modify(feature));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeature(@PathVariable Long id) throws ResourceNotFoundException {
        featureService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("deleted successfully");
    }
}
