package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
}
