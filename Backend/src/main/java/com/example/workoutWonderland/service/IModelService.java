package com.example.workoutWonderland.service;

import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;

import java.util.Set;

public interface IModelService<T> {
    T create(T t) throws AlreadyExistsException;

    T findById(Long id) throws ResourceNotFoundException;

    T modify(T t) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

    Set<T> findAll() throws ResourceNotFoundException;
}
