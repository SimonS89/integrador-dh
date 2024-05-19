package com.example.workoutWonderland.controller;


import com.example.workoutWonderland.configuration.security.service.JwtService;
import com.example.workoutWonderland.dto.request.AssessmentRequestDTO;
import com.example.workoutWonderland.dto.request.AuthRequestDTO;
import com.example.workoutWonderland.dto.request.UserRegisterRequestDTO;
import com.example.workoutWonderland.dto.response.UserResponseDTO;
import com.example.workoutWonderland.entity.Product;
import com.example.workoutWonderland.entity.UserEntity;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(IUserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserRegisterRequestDTO user) throws AlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @GetMapping()
    public ResponseEntity<Set<UserEntity>> listUsers() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<UserEntity> userById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findById(id));
    }

    @PutMapping("modify")
    public ResponseEntity<UserEntity> updateUser(@Valid @RequestBody UserEntity user) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.modify(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User delete successfully");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserResponseDTO> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) throws ResourceNotFoundException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authenticate.isAuthenticated()) {
            UserResponseDTO userResponseDTO = userService.findByUsername(authRequestDTO.getUsername());
            userResponseDTO.setToken(jwtService.generateToken(authRequestDTO.getUsername()));
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
        }
        throw new UsernameNotFoundException("Invalid user request");
    }

    @PatchMapping("/modify_roles/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserEntity> modifyUserRoles(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.modifyUserRoles(userId));
    }

    @GetMapping("/resend_email/{username}")
    public ResponseEntity<String> resendEmail(@PathVariable String username) throws ResourceNotFoundException {
        userService.resendEmail(username);
        return ResponseEntity.status(HttpStatus.OK).body("Email resent correctly");
    }

    @PostMapping("/assessProduct")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Product> assessProduct(@RequestBody AssessmentRequestDTO assessmentRequestDTO) throws AlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.ok(userService.asess(assessmentRequestDTO));
    }
}
