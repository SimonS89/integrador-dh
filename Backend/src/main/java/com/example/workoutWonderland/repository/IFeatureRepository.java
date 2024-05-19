package com.example.workoutWonderland.repository;

import com.example.workoutWonderland.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeatureRepository extends JpaRepository<Feature, Long> {
}
