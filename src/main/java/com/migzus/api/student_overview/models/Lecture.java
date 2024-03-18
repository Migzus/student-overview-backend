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
@Table(name = "lecture")
public class Lecture extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;

    @OneToMany(mappedBy = "lecture")
    @JsonIgnoreProperties("lecture")
    private List<Exercise> exercises;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "start_date", "end_date" })
    private Classroom classroom;

    @Override
    public boolean haveNullFields() {
        return name == null || startDate == null;
    }

    @Override
    public void copyOverData(Model model) {
        Lecture _other = (Lecture) model;

        name = _other.name;
        description = _other.description;
        startDate = _other.startDate;
        endDate = _other.endDate;

        exercises = _other.exercises;
        classroom = _other.classroom;
    }
}
