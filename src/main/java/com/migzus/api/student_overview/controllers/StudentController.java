package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student")
public class StudentController extends ControllerTemplate<Student> {
    // student POST request needs an endpoint for adding an existing classroom
}
