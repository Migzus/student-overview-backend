package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Evaluation;
import com.migzus.api.student_overview.models.Exercise;
import com.migzus.api.student_overview.models.Student;
import com.migzus.api.student_overview.repositories.ExerciseRepository;
import com.migzus.api.student_overview.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("evaluation")
public class EvaluationController extends AdvancedControllerTemplate<Evaluation, EvaluationController.EvaluationRequest> {
    @Autowired
    protected StudentRepository studentRepository;
    @Autowired
    protected ExerciseRepository exerciseRepository;

    public record EvaluationRequest(Integer grade, Integer studentId, Integer exerciseId) {
        boolean hasNullValues() {
            return grade == null || studentId == null || exerciseId == null;
        }
    }

    @Override
    public ResponseEntity<Evaluation> create(@RequestBody EvaluationRequest request) {
        if (request.hasNullValues()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some or one of the provided values is null.");

        final Student _student = studentRepository.findById(request.studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a student with the provided id."));
        final Exercise _exercise = exerciseRepository.findById(request.exerciseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an exercise with the provided id."));
        final Evaluation _newEvaluation = new Evaluation(request.grade, _student, _exercise);

        _student.getEvaluations().add(_newEvaluation);
        _exercise.getEvaluations().add(_newEvaluation);

        return new ResponseEntity<>(repository.save(_newEvaluation), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Evaluation> update(@PathVariable Integer id, @RequestBody EvaluationRequest request) {
        if (request.hasNullValues()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some or one of the provided values is null.");

        final Evaluation _evaluation = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an evaluation with the provided id."));
        final Student _student = studentRepository.findById(request.studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a student with the provided id."));
        final Exercise _exercise = exerciseRepository.findById(request.exerciseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an exercise with the provided id."));

        _evaluation.setGrade(request.grade);
        _evaluation.setExercise(_exercise);
        _evaluation.setStudent(_student);
        _evaluation.touch();

        return new ResponseEntity<>(repository.save(_evaluation), HttpStatus.OK);
    }
}
