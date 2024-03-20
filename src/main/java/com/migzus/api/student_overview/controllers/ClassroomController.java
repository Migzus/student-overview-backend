package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.*;
import com.migzus.api.student_overview.repositories.ExerciseRepository;
import com.migzus.api.student_overview.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("classroom")
public class ClassroomController extends AdvancedControllerTemplate<Classroom, ClassroomController.ClassroomRequest> {
    @Autowired
    protected ExerciseRepository exerciseRepository;
    @Autowired
    protected TeacherRepository teacherRepository;

    // adding a new classroom, we need to take into account for teachers

    public record ClassroomRequest(String name, String startDate, String endDate, Integer teacherId) {
        public boolean hasNullFields() {
            return name == null || startDate == null || endDate == null || teacherId == null;
        }
    }

    @Override
    public ResponseEntity<Classroom> create(@RequestBody ClassroomRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The JSON body has null fields. Make sure you have the appropriate keys.");

        final Teacher _teacher = teacherRepository.findById(request.teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find teacher with that ID."));
        final Classroom _classroom = new Classroom(request.name, request.startDate, request.endDate, _teacher);

        _teacher.getClassrooms().add(_classroom);

        return new ResponseEntity<>(repository.save(_classroom), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Classroom> update(@PathVariable Integer id, @RequestBody ClassroomRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The JSON body has null fields. Make sure you have the appropriate keys.");

        final Teacher _teacher = teacherRepository.findById(request.teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find teacher with that ID."));
        final Classroom _classroom = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a classroom with that ID."));

        _classroom.setName(request.name);
        _classroom.setStartDate(request.startDate);
        _classroom.setEndDate(request.endDate);
        _classroom.setTeacher(_teacher);

        return new ResponseEntity<>(repository.save(_classroom), HttpStatus.OK);
    }

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
