package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Lecture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lecture")
public class LectureController extends ControllerTemplate<Lecture> {
}
