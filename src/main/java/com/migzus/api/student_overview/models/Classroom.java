package com.migzus.api.student_overview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "classroom")
public class Classroom extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;

    @OneToMany(mappedBy = "classroom")
    @JsonIgnoreProperties("classroom")
    private List<Student> students;

    @OneToMany(mappedBy = "classroom")
    @JsonIgnoreProperties("classroom")
    private List<Lecture> lectures;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "firstName", "lastName", "email", "phone" })
    private Teacher teacher;

    public Classroom(String name, String startDate, String endDate, Teacher teacher) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
    }

    @Override
    public boolean haveNullFields() {
        return name == null || startDate == null || endDate == null;
    }

    @Override
    public void copyOverData(Model model) {
        Classroom _other = (Classroom) model;

        name = _other.name;
        startDate = _other.startDate;
        endDate = _other.endDate;

        students = _other.students;
        teacher = _other.teacher;
        lectures = _other.lectures;
    }
}
