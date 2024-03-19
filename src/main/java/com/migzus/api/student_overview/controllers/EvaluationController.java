package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Evaluation;
import com.migzus.api.student_overview.models.Exercise;
import com.migzus.api.student_overview.models.Student;
import com.migzus.api.student_overview.repositories.ExerciseRepository;
import com.migzus.api.student_overview.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("evaluation")
public class EvaluationController extends ControllerTemplate<Evaluation> {
    @Autowired
    protected StudentRepository studentRepository;
    @Autowired
    protected ExerciseRepository exerciseRepository;

    public record EvaluationRequest(Integer grade, Integer studentId, Integer exerciseId) {
        boolean hasNullValues() {
            return grade == null || studentId == null || exerciseId == null;
        }
    }

    @PostMapping("add")
    public ResponseEntity<Evaluation> create(@RequestBody EvaluationRequest request) {
        if (request.hasNullValues()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some or one of the provided values is null.");

        final Student _student = studentRepository.findById(request.studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a student with the provided id."));
        final Exercise _exercise = exerciseRepository.findById(request.exerciseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an exercise with the provided id."));
        Evaluation _newEvaluation = new Evaluation(request.grade, _student, _exercise);

        _student.getEvaluations().add(_newEvaluation);
        _exercise.getEvaluations().add(_newEvaluation);

        return new ResponseEntity<>(repository.save(_newEvaluation), HttpStatus.CREATED);
    }
}
