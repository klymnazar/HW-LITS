package com.lits.springboot.service;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.model.Course;

import java.util.List;

public interface CourseService {

    CourseDto create(CourseDto courseDto);
    CourseDto getOne(Integer id);
    CourseDto update(Integer id, String newCourseName, TeacherDto teacher);
    void delete(Integer id);
    List<CourseDto> getAll();
    List<String> getAllCoursesWithoutTeacher();
    Course updateCourseTeacher(Integer courseId, Integer teacherId);
    CourseDto addTeachersToCourse(Integer courseId, List<TeacherDto> teachers);
    List<CourseDto> getAllCourses(String type, Integer numberMonth);

}
