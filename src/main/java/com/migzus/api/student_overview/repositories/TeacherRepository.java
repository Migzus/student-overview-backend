package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
