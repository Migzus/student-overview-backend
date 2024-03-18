package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
}
