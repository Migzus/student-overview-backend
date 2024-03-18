package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
}
