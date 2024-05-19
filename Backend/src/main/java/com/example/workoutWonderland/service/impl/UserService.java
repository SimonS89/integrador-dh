package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.dto.request.AssessmentRequestDTO;
import com.example.workoutWonderland.dto.request.UserRegisterRequestDTO;
import com.example.workoutWonderland.dto.response.UserResponseDTO;
import com.example.workoutWonderland.entity.Assessment;
import com.example.workoutWonderland.entity.Product;
import com.example.workoutWonderland.entity.RoleEntity;
import com.example.workoutWonderland.entity.UserEntity;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.IUserEntityRepository;
import com.example.workoutWonderland.service.IAssessmentService;
import com.example.workoutWonderland.service.IEmailService;
import com.example.workoutWonderland.service.IUserService;
import com.example.workoutWonderland.utils.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

    private final IUserEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final IEmailService emailService;
    private final IAssessmentService assessmentService;

    public UserService(IUserEntityRepository userRepository, PasswordEncoder passwordEncoder, ObjectMapper objectMapper, IEmailService emailService, IAssessmentService assessmentService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.assessmentService = assessmentService;
    }

    @Override
    public UserEntity create(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserEntity findById(Long id) throws ResourceNotFoundException {
        Optional<UserEntity> userSearch = userRepository.findById(id);
        if (userSearch.isEmpty()) throw new ResourceNotFoundException("user not found");
        return userSearch.get();
    }

    public UserResponseDTO findByUsername(String username) throws ResourceNotFoundException {
        Optional<UserEntity> userSearch = userRepository.findByUsername(username);
        if (userSearch.isEmpty()) throw new ResourceNotFoundException("user not found");
        return objectMapper.convertValue(userSearch, UserResponseDTO.class);
    }

    @Override
    public UserEntity modify(UserEntity user) throws ResourceNotFoundException {
        Optional<UserEntity> userSearch = userRepository.findById(user.getId());
        if (userSearch.isEmpty()) throw new ResourceNotFoundException("user not found");
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<UserEntity> userSearch = userRepository.findById(id);
        if (userSearch.isEmpty()) throw new ResourceNotFoundException("User not found");
        userRepository.deleteById(id);
    }

    @Override
    public Set<UserEntity> findAll() throws ResourceNotFoundException {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) throw new ResourceNotFoundException("There are no users stored");
        return new HashSet<>(users);
    }

    @Override
    public UserEntity create(UserRegisterRequestDTO userRegisterRequestDTO) throws AlreadyExistsException, ResourceNotFoundException {
        UserEntity user = objectMapper.convertValue(userRegisterRequestDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity(1l, Roles.USER));
        user.setRoles(roles);
        emailService.sendMail(userRegisterRequestDTO.getUsername(), "Welcome to WorkoutWonderland", emailBody(userRegisterRequestDTO.getName(), userRegisterRequestDTO.getLastname()));
        return userRepository.save(user);
    }

    @Override
    public UserEntity modifyUserRoles(Long userId) throws ResourceNotFoundException {
        Optional<UserEntity> user = userRepository.findById(userId);
        RoleEntity admin = new RoleEntity(2l, Roles.ADMIN);
        if (user.isEmpty()) throw new ResourceNotFoundException("User not found");
        UserEntity userEntity = user.get();
        if (userEntity.getRoles().size() == 1) userEntity.getRoles().add(admin);
        else userEntity.getRoles().remove(admin);
        return userRepository.save(userEntity);
    }

    @Override
    public void resendEmail(String username) throws ResourceNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new ResourceNotFoundException("user not found");
        emailService.sendMail(username, "Re: Welcome mail", emailBody(user.get().getName(), user.get().getLastname()));
    }

    private String emailBody(String name, String lastname) {
        return "Dear " + name + " " + (lastname) + ",\n \n" + "For login in continue to : http://grupo6california.s3-website-us-west-1.amazonaws.com/login-user" + "\n \n" + "Greetings, WorkoutWonderland staff.";
    }

    @Override
    public Product asess(AssessmentRequestDTO assessmentRequestDTO) throws AlreadyExistsException, ResourceNotFoundException {
        Optional<Product> productFound = userRepository.productBookedByUser(assessmentRequestDTO.getUserId(), assessmentRequestDTO.getProductId());
        if (productFound.isEmpty()) throw new ResourceNotFoundException("no match");
        Assessment assessment = objectMapper.convertValue(assessmentRequestDTO, Assessment.class);
        UserEntity user = new UserEntity();
        user.setId(assessmentRequestDTO.getUserId());
        assessment.setUser(user);
        assessment.setProduct(productFound.get());
        productFound.get().getAssessments().add(assessmentService.create(assessment));
        return productFound.get();
    }
}
