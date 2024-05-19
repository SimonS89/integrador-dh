package com.example.workoutWonderland.service;

import com.example.workoutWonderland.dto.request.AssessmentRequestDTO;
import com.example.workoutWonderland.dto.request.UserRegisterRequestDTO;
import com.example.workoutWonderland.dto.response.UserResponseDTO;
import com.example.workoutWonderland.entity.Product;
import com.example.workoutWonderland.entity.UserEntity;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;

public interface IUserService extends IModelService<UserEntity> {

    UserEntity create(UserRegisterRequestDTO userRegisterRequestDTO) throws AlreadyExistsException, ResourceNotFoundException;

    UserEntity modifyUserRoles(Long userId) throws ResourceNotFoundException;

    void resendEmail(String username) throws ResourceNotFoundException;

    UserResponseDTO findByUsername(String username) throws ResourceNotFoundException;

    Product asess(AssessmentRequestDTO assessmentRequestDTO) throws AlreadyExistsException, ResourceNotFoundException;
}
