package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Exercise;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exercise")
public class ExerciseController extends ControllerTemplate<Exercise> {
    // when adding an exercise, we need to take lecture into account
}
