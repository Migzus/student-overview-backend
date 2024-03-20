package com.migzus.api.student_overview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.migzus.api.student_overview.utils.Util;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exercise")
public class Exercise extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "link_to_repo")
    private String linkToRepo;
    @Column(name = "last_updated")
    private String lastUpdated;

    @OneToMany(mappedBy = "exercise")
    @JsonIgnoreProperties("exercise")
    private List<Evaluation> evaluations;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "description", "startDate", "endDate" })
    private Lecture lecture;

    public Exercise() {
        touch();
    }

    public Exercise(String name, String description, String linkToRepo, Lecture lecture) {
        this.name = name;
        this.description = description;
        this.linkToRepo = linkToRepo;
        this.lecture = lecture;

        touch();
    }

    public void touch() {
        lastUpdated = Util.getCurrentDate();
    }

    @Override
    public boolean haveNullFields() {
        return name == null;
    }

    @Override
    public void copyOverData(Model model) {
        Exercise _other = (Exercise) model;

        name = _other.name;
        description = _other.description;
        linkToRepo = _other.linkToRepo;

        evaluations = _other.evaluations;
        lecture = _other.lecture;

        touch();
    }
}
