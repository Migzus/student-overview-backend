package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Evaluation;
import com.migzus.api.student_overview.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("classroom/{classroomID}/lecture/{lectureID}/exercise/{exerciseID}/evaluation")
public class EvaluationController {
    @Autowired
    protected EvaluationRepository repository;

    @Autowired
    protected ClassroomRepository classroomRepository;
    @Autowired
    protected LectureRepository lectureRepository;
    @Autowired
    protected ExerciseRepository exerciseRepository;

    @GetMapping
    public List<Evaluation> getAll(@PathVariable final Integer classroomID, @PathVariable final Integer lectureID, @PathVariable final Integer exerciseID) {
        //classroomRepository.getReferenceById(classroomID)

        return repository.findAll();
    }
}
