package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.entity.Feature;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.IFeatureRepository;
import com.example.workoutWonderland.service.IFeatureService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class FeatureService implements IFeatureService {

    private final IFeatureRepository featureRepository;
    private static final String NOT_FOUND = "Feature not found";

    public FeatureService(IFeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }


    @Override
    public Feature create(Feature feature) throws AlreadyExistsException {
        return featureRepository.save(feature);
    }

    @Override
    public Feature findById(Long id) throws ResourceNotFoundException {
        Optional<Feature> featureSearch = featureRepository.findById(id);
        if (featureSearch.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return featureSearch.get();
    }

    @Override
    public Feature modify(Feature feature) throws ResourceNotFoundException {
        Optional<Feature> featureSearch = featureRepository.findById(feature.getId());
        if (featureSearch.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return featureRepository.save(feature);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Feature> featureSearch = featureRepository.findById(id);
        if (featureSearch.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        featureRepository.deleteById(id);
    }

    @Override
    public Set<Feature> findAll() throws ResourceNotFoundException {
        Set<Feature> features = new HashSet<>(featureRepository.findAll());
        if(features.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return features;
    }
}
