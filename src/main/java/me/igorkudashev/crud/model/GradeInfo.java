package me.igorkudashev.crud.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "grade_info")
@Data
@Accessors(chain = true)
public class GradeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne()
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private String grade;

    private LocalDate date;
}