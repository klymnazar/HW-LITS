package com.lits.springboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CourseDto {
    private Integer id;
    private String courseName;
    private Integer teacherId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TeacherDto> teachers;
    private List<Integer> teacherIds;

}
