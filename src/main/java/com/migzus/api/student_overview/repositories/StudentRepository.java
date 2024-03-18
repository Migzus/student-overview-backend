package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
