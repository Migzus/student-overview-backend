package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Classroom;
import com.migzus.api.student_overview.models.Student;
import com.migzus.api.student_overview.repositories.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("student")
public class StudentController extends AdvancedControllerTemplate<Student, StudentController.StudentRequest> {
    @Autowired
    protected ClassroomRepository classroomRepository;

    public record StudentRequest(String firstName, String lastName, String email, String createdAt, Integer classroomId) {
        public boolean hasNullFields() {
            return firstName == null || lastName == null || email == null || classroomId == null;
        }
    }

    @Override
    public ResponseEntity<Student> create(@RequestBody StudentRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The JSON body has null keys. Make sure you have assigned all the correct keys.");

        final Classroom _classroom = classroomRepository.findById(request.classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a classroom with that ID."));
        final Student _student = new Student(request.firstName, request.lastName, request.email, request.createdAt, _classroom);

        _classroom.getStudents().add(_student);

        return new ResponseEntity<>(repository.save(_student), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Student> update(@PathVariable Integer id, @RequestBody StudentRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The JSON body has null keys. Make sure you have assigned all the correct keys.");

        final Classroom _classroom = classroomRepository.findById(request.classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a classroom with that ID."));
        final Student _student = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a student with that ID."));

        _student.setFirstName(request.firstName);
        _student.setLastName(request.lastName);
        _student.setEmail(request.email);
        _student.setCreatedAt(request.createdAt);
        _student.setClassroom(_classroom);

        return new ResponseEntity<>(repository.save(_student), HttpStatus.OK);
    }
}
