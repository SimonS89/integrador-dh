package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.entity.Policy;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.IPolicyRepository;
import com.example.workoutWonderland.service.IPolicyService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PolicyService implements IPolicyService {

    private final IPolicyRepository policyRepository;

    public PolicyService(IPolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public Policy create(Policy policy) throws AlreadyExistsException {
        return policyRepository.save(policy);
    }

    @Override
    public Policy findById(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Policy modify(Policy policy) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {

    }

    @Override
    public Set<Policy> findAll() throws ResourceNotFoundException {
        return new HashSet<>(policyRepository.findAll());
    }
}
