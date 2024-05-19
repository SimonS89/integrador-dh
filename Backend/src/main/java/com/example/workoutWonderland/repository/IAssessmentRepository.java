package com.example.workoutWonderland.repository;

import com.example.workoutWonderland.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssessmentRepository extends JpaRepository<Assessment, Long> {
}
