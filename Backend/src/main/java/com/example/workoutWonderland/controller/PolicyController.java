package com.example.workoutWonderland.controller;

import com.example.workoutWonderland.entity.Policy;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.service.IPolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/policy")
public class PolicyController {

    private final IPolicyService policyService;

    public PolicyController(IPolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) throws AlreadyExistsException {
        return ResponseEntity.ok(policyService.create(policy));
    }

    @GetMapping()
    public ResponseEntity<Set<Policy>> listPolicies() throws ResourceNotFoundException {
        return ResponseEntity.ok(policyService.findAll());
    }
}
