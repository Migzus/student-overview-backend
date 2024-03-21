package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByEmail(String email);

    Boolean existsByEmail(String email);
}
