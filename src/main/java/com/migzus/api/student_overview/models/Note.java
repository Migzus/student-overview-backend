package com.migzus.api.student_overview.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "note")
public class Note extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "first_name", "last_name", "email", "phone" })
    private Teacher teacher;

    public Note(String content, Teacher teacher) {
        this.content = content;
        this.teacher = teacher;
    }

    @Override
    public boolean haveNullFields() {
        return content == null || teacher == null;
    }

    @Override
    public void copyOverData(Model model) {
        Note _other = (Note) model;

        content = _other.content;
        teacher = _other.teacher;
    }
}
