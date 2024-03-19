package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.*;
import com.migzus.api.student_overview.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("classroom")
public class ClassroomController extends ControllerTemplate<Classroom> {
    @Autowired
    protected ExerciseRepository exerciseRepository;

    // adding a new classroom, we need to take into account for teachers

    @GetMapping("{classroomID}/lecture/{lectureID}/exercise")
    public List<Exercise> getAll(@PathVariable final Integer classroomID, @PathVariable final Integer lectureID) {
        final ArrayList<Exercise> _outExercises = new ArrayList<>();
        final Classroom _classroom = repository.findById(classroomID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a classroom with that id."));
        Lecture _lecture = null;

        for (Lecture lecture : _classroom.getLectures()) {
            if (Objects.equals(lecture.getId(), lectureID)) {
                _lecture = lecture;
                break;
            }
        }

        if (_lecture == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an appropriate lecture related to the provided classroom");

        for (Exercise exercise : exerciseRepository.findAll())
            if (exercise.getLecture() == _lecture)
                _outExercises.add(exercise);

        return _outExercises;
    }

    @GetMapping("{classroomID}/student")
    public List<Student> getAllRelatedToClassroom(@PathVariable final Integer classroomID) {
        return repository.findById(classroomID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No classroom with such an ID.")).getStudents();
    }

    @GetMapping("{classroomID}/student/{id}")
    public ResponseEntity<Student> getById(@PathVariable final Integer classroomID, @PathVariable final Integer id) {
        Classroom _classroom = repository.findById(classroomID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No classroom with such an ID."));

        for (Student student : _classroom.getStudents())
            if (Objects.equals(student.getId(), id))
                return new ResponseEntity<>(student, HttpStatus.OK);

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a student related to class.");
    }

    @GetMapping("{classroomID}/lecture/{lectureID}/exercise/{exerciseID}/evaluation")
    public List<Evaluation> getAll(@PathVariable final Integer classroomID, @PathVariable final Integer lectureID, @PathVariable final Integer exerciseID) {
        for (Exercise exercise : getAll(classroomID, lectureID))
            if (Objects.equals(exercise.getId(), exerciseID))
                return exercise.getEvaluations();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a relational exercise to the provided lecture and classroom.");
    }
}
