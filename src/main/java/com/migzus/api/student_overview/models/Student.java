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
@Table(name = "student")
public class Student extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    private String createdAt;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    private List<Evaluation> evaluations;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "startDate", "endDate" })
    private Classroom classroom;

    public Student(String firstName, String lastName, String email, String createdAt, Classroom classroom) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.classroom = classroom;
    }

    @Override
    public boolean haveNullFields() {
        return firstName == null || lastName == null || email == null;
    }

    @Override
    public void copyOverData(Model model) {
        Student _other = (Student) model;

        firstName = _other.firstName;
        lastName = _other.lastName;
        email = _other.email;
        createdAt = _other.createdAt;

        evaluations = _other.evaluations;
    }
}
