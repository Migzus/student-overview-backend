package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.repositories.ClassroomRepository;
import com.migzus.api.student_overview.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("classroom/{classroomID}/student")
public class StudentController {
    @Autowired
    protected StudentRepository repository;

    @Autowired
    protected ClassroomRepository classroomRepository;
}
