package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Note;
import com.migzus.api.student_overview.models.Teacher;
import com.migzus.api.student_overview.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class NoteController extends AdvancedControllerTemplate<Note, NoteController.NoteRequest> {
    @Autowired
    protected TeacherRepository teacherRepository;

    public record NoteRequest(String content, Integer teacherId) {
        public boolean hasNullFields() {
            return content == null || teacherId == null;
        }
    }

    @Override
    public ResponseEntity<Note> create(NoteRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The JSON body has null keys. Make sure you have assigned all the correct keys.");

        final Teacher _teacher = teacherRepository.findById(request.teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a teacher by the given ID."));
        final Note _note = new Note(request.content, _teacher);

        _teacher.getNotes().add(_note);

        return new ResponseEntity<>(repository.save(_note), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Note> update(Integer id, NoteRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The JSON body has null keys. Make sure you have assigned all the correct keys.");

        final Teacher _teacher = teacherRepository.findById(request.teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a teacher by the given ID."));
        final Note _note = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a note by the given ID."));

        _note.setContent(request.content);
        _note.setTeacher(_teacher);

        return new ResponseEntity<>(_note, HttpStatus.OK);
    }
}
