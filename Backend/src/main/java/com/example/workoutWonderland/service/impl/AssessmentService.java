package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.entity.Assessment;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.IAssessmentRepository;
import com.example.workoutWonderland.service.IAssessmentService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AssessmentService implements IAssessmentService {

    private final IAssessmentRepository assessmentRepository;

    public AssessmentService(IAssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    public Assessment create(Assessment assessment) throws AlreadyExistsException {
        return assessmentRepository.save(assessment);
    }

    @Override
    public Assessment findById(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Assessment modify(Assessment assessment) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {

    }

    @Override
    public Set<Assessment> findAll() throws ResourceNotFoundException {
        return null;
    }
}
