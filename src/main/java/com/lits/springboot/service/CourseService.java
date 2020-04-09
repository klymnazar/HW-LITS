package com.lits.springboot.service;

import com.lits.springboot.model.Course;
import com.lits.springboot.model.Teacher;

import java.util.List;

public interface CourseService {

    Course create(Course course);

    Course getOne(Integer id);

    Course update(Integer id, String newCourseName, Teacher teacher);

    void delete(Integer id);

    List<Course> getAll();

    List<String> getAllCoursesWithoutTeacher();

    Course updateCourseTeacher(Integer courseId, Integer teacherId);

    Course addTeachersToCourse(Integer courseId, List<Teacher> teachers);

///////////////////////////////

//    List<Course> getAllOrderByDate();
//
//    List<Course> getAllAfterDate();
//
//    List<Course> getAllBeforeDate();
//
//    List<Course> getAllContinuesNow();
//
//    List<Course> getAllCoursesDurationMonths(int numberMonths);

    List<Course> getAllCourses(String type, Integer numberMonth);

//    List<Course> getAllCoursesDuration(Integer month);
}
