package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Exercise;
import com.migzus.api.student_overview.models.Lecture;
import com.migzus.api.student_overview.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("exercise")
public class ExerciseController extends AdvancedControllerTemplate<Exercise, ExerciseController.ExerciseRequest> {
    @Autowired
    protected LectureRepository lectureRepository;

    public record ExerciseRequest(String name, String description, String linkToRepo, Integer lectureId) {
        public boolean hasNullFields() {
            return name == null || lectureId == null;
        }
    }

    @Override
    public ResponseEntity<Exercise> create(@RequestBody ExerciseRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided Exercise JSON body has null keys. Make sure you have set the needed data appropriately.");

        final Lecture _lecture = lectureRepository.findById(request.lectureId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a lecture with the provided ID."));
        final Exercise _exercise = new Exercise(request.name, request.description, request.linkToRepo, _lecture);

        _lecture.getExercises().add(_exercise);

        return new ResponseEntity<>(repository.save(_exercise), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Exercise> update(@PathVariable Integer id, @RequestBody ExerciseRequest request) {
        if (request.hasNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided Exercise JSON body has null keys. Make sure you have set the needed data appropriately.");

        final Lecture _lecture = lectureRepository.findById(request.lectureId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a lecture with the provided ID."));
        final Exercise _exercise = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an exercise with the provided ID."));

        _exercise.setName(request.name);
        _exercise.setDescription(request.description);
        _exercise.setLinkToRepo(request.linkToRepo);
        _exercise.setLecture(_lecture);
        _exercise.touch();

        return new ResponseEntity<>(repository.save(_exercise), HttpStatus.OK);
    }
}
