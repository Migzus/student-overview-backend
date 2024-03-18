package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
}
