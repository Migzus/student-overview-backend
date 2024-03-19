package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Classroom;
import com.migzus.api.student_overview.models.Teacher;
import com.migzus.api.student_overview.repositories.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("teacher")
public class TeacherController extends ControllerTemplate<Teacher> {
    @Autowired
    protected ClassroomRepository classroomRepository;

    @GetMapping("{id}/classroom")
    public List<Classroom> getAllClassrooms(@PathVariable final Integer id) {
        final ArrayList<Classroom> _relatedClassrooms = new ArrayList<>();
        final Teacher _teacher = getById(id).getBody();

        for (Classroom classroom : classroomRepository.findAll())
            if (classroom.getTeacher() == _teacher)
                _relatedClassrooms.add(classroom);

        return _relatedClassrooms;
    }
}
