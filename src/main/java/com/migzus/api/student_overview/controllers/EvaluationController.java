package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Evaluation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("evaluation")
public class EvaluationController extends ControllerTemplate<Evaluation> {
}
