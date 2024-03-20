package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
