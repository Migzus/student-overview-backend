package com.migzus.api.student_overview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "teacher")
public class Teacher extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnoreProperties("teacher")
    private List<Classroom> classrooms;

    @Override
    public boolean haveNullFields() {
        return firstName == null || lastName == null || email == null;
    }

    @Override
    public void copyOverData(Model model) {
        Teacher _other = (Teacher) model;

        firstName = _other.firstName;
        lastName = _other.lastName;
        email = _other.email;
        phone = _other.phone;

        classrooms = _other.classrooms;
    }
}
