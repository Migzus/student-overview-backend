package com.migzus.api.student_overview.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "teacher",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
    })
public class Teacher extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnoreProperties("teacher")
    private List<Classroom> classrooms;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnoreProperties("teacher")
    private List<Note> notes;

    @NotBlank
    @Column(length = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teacher_roles", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Teacher(String firstName, String lastName, String email, String phone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

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
        notes = _other.notes;

        roles = _other.roles;
    }
}
