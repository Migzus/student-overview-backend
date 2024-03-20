package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Classroom;
import com.migzus.api.student_overview.models.Lecture;
import com.migzus.api.student_overview.repositories.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("lecture")
public class LectureController extends AdvancedControllerTemplate<Lecture, LectureController.LectureRequest> {
    @Autowired
    protected ClassroomRepository classroomRepository;

    public record LectureRequest(String name, String description, String startDate, String endDate, Integer classroomId) {
        public boolean hasNullFields() {
            return name == null || startDate == null || classroomId == null;
        }
    }

    @Override
    public ResponseEntity<Lecture> create(@RequestBody LectureRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided Lecture JSON body has null keys. Make sure you have set the needed data appropriately.");

        final Classroom _classroom = classroomRepository.findById(request.classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a classroom with the given ID."));
        final Lecture _lecture = new Lecture(request.name, request.description, request.startDate, request.endDate, _classroom);

        _classroom.getLectures().add(_lecture);

        return new ResponseEntity<>(repository.save(_lecture), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Lecture> update(@PathVariable Integer id, @RequestBody LectureRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided Lecture JSON body has null keys. Make sure you have set the needed data appropriately.");

        final Classroom _classroom = classroomRepository.findById(request.classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a classroom with the given ID."));
        final Lecture _lecture = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a lecture with the given ID."));

        _lecture.setName(request.name);
        _lecture.setDescription(request.description);
        _lecture.setStartDate(request.startDate);
        _lecture.setEndDate(request.endDate);
        _lecture.setClassroom(_classroom);

        return new ResponseEntity<>(repository.save(_lecture), HttpStatus.OK);
    }
}
