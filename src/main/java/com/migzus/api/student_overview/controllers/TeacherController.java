package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Classroom;
import com.migzus.api.student_overview.models.Note;
import com.migzus.api.student_overview.models.Teacher;
import com.migzus.api.student_overview.repositories.ClassroomRepository;
import com.migzus.api.student_overview.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("teacher")
public class TeacherController extends ControllerTemplate<Teacher> {
    @Autowired
    protected ClassroomRepository classroomRepository;
    @Autowired
    protected NoteRepository noteRepository;

    @GetMapping("{id}/classroom")
    public List<Classroom> getAllClassrooms(@PathVariable final Integer id) {
        final ArrayList<Classroom> _relatedClassrooms = new ArrayList<>();
        final Teacher _teacher = getById(id).getBody();

        for (Classroom classroom : classroomRepository.findAll())
            if (classroom.getTeacher() == _teacher)
                _relatedClassrooms.add(classroom);

        return _relatedClassrooms;
    }

    @GetMapping("{id}/note")
    public List<Note> getNotes(@PathVariable final Integer id) {
        final ArrayList<Note> _relatedNotes = new ArrayList<>();
        final Teacher _teacher = getById(id).getBody();

        for (Note note : noteRepository.findAll())
            if (note.getTeacher() == _teacher)
                _relatedNotes.add(note);

        return _relatedNotes;
    }

    @PostMapping("{id}/note")
    public ResponseEntity<Note> createNote(@PathVariable final Integer id, @RequestBody final NoteController.NoteRequest request) {
        final Teacher _teacher = getById(id).getBody();
        if (_teacher == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        final Note _note = new Note(request.content(), _teacher);
        _teacher.getNotes().add(_note);

        return new ResponseEntity<>(noteRepository.save(_note), HttpStatus.CREATED);
    }
}
