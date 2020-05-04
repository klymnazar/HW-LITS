package com.lits.springboot.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @FutureOrPresent(message = "StartDate is not valid. Enter new Date.")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @FutureOrPresent(message = "EndDate is not valid. Enter new Date.")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_course", joinColumns = {@JoinColumn(name = "courseId")},
            inverseJoinColumns = {@JoinColumn(name = "teacherId")})
    private List<Teacher> teacherList;

}
