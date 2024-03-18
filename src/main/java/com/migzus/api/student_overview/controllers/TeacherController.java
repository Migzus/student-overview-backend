package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Teacher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teacher")
public class TeacherController extends ControllerTemplate<Teacher> {
}
