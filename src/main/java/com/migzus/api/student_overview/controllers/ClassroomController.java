package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Classroom;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("classroom")
public class ClassroomController extends ControllerTemplate<Classroom> {
}
