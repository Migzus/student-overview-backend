package com.migzus.api.student_overview.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.migzus.api.student_overview.utils.Util;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "evaluation")
public class Evaluation extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grade")
    private Integer grade;
    @Column(name = "last_updated")
    private String lastUpdated;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "firstName", "lastName", "email" })
    private Student student;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "description", "linkToRepo", "lastUpdated" })
    private Exercise exercise;

    public Evaluation() {
        grade = 0;
        lastUpdated = Util.getCurrentDate();
    }

    public Evaluation(Integer grade) {
        this.grade = grade;
        lastUpdated = Util.getCurrentDate();
    }

    @Override
    public boolean haveNullFields() {
        return grade == null;
    }

    @Override
    public void copyOverData(Model model) {
        Evaluation _other = (Evaluation) model;

        grade = _other.grade;
        lastUpdated = Util.getCurrentDate();

        student = _other.student;
        exercise = _other.exercise;
    }
}
