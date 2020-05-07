package com.lits.springboot.controller;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.service.CourseService;
import com.lits.springboot.service.StudentService;
import com.lits.springboot.service.TeacherService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public CourseController(CourseService courseService, TeacherService teacherService, StudentService studentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping("/courses/{id}")
    public CourseDto getById(@PathVariable(name = "id") Integer id) {
        log.info("Get course by id = " + id);
        return courseService.getOne(id);
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourses(@RequestParam(name = "durationType", required = false) String durationType,
                                         @RequestParam(name = "durationMonths", required = false) Integer durationMonths) {
        log.info("Get all courses");
        return courseService.getAllCourses(durationType, durationMonths);
    }

    @GetMapping("/courses/byStudent/{id}")
    public List<CourseDto> getAllCoursesByStudent(@PathVariable(name = "id") Integer id) {
        log.info("Get all courses by Student");
        return courseService.getAllCourseByStudent(id);
    }

    @GetMapping("/courses/byStudent/{id}/count")
    public Integer countAllCoursesByStudent(@PathVariable(name = "id") Integer id) {
        log.info("Count all courses by Student");
        return courseService.getAllCourseByStudent(id).size();
    }

    @GetMapping("/courses/byStudentTeacher/{studentId}/{teacherId}")
    public List<CourseDto> getAllCoursesByStudentAndTeacher(@PathVariable(name = "studentId") Integer studentId, @PathVariable(name = "teacherId") Integer teacherId) {
        log.info("Get all courses by Student and Teacher");
        return courseService.getAllCourseByStudentAndTeacher(studentId, teacherId);
    }



    @PutMapping("/courses/{id}")
    public CourseDto update(@PathVariable(name = "id") Integer id, @RequestBody CourseDto courseDto) {
        log.info("Update course by id = " + id);
        return courseService.update(id, courseDto.getCourseName());
    }

    @PutMapping("/courses/{id}/students")
    public CourseDto addStudents(@PathVariable(name = "id") Integer courseId, @RequestBody CourseDto courseDto) {
        List<Integer> studentIds = new ArrayList<>();
        for (Integer studentId : courseDto.getStudentIds()) {
            studentIds.add(studentService.getOne(studentId).getId());
        }
        log.info("Add students to course");
        return courseService.addStudentsToCourse(courseId, studentIds);
    }

    @PutMapping("/courses/{id}/removeStudents")
    public CourseDto removeStudent(@PathVariable(name = "id") Integer courseId, @RequestBody CourseDto courseDto) {
        List<Integer> studentIds = new ArrayList<>();
        for (Integer studentId : courseDto.getStudentIds()) {
            studentIds.add(studentService.getOne(studentId).getId());
        }
        log.info("Remove student from course");
        return courseService.removeStudentsFromCourse(courseId, studentIds);
    }

    @PutMapping("/courses/{id}/teachers")
    public CourseDto addTeachers(@PathVariable(name = "id") Integer courseId, @RequestBody CourseDto courseDto) {
        List<Integer> teacherIds = new ArrayList<>();
        for (Integer teacherId : courseDto.getTeacherIds()) {
            teacherIds.add(teacherService.getOne(teacherId).getId());
        }
        log.info("Add teachers to course");
        return courseService.addTeachersToCourse(courseId, teacherIds);
    }



    @PostMapping("/courses")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CourseDto create(@RequestBody CourseDto courseDto) {
        log.info("Create new course");
        return courseService.create(courseDto);
    }



    @DeleteMapping("/courses/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        log.info("Delete course by id= " + id);
        courseService.delete(id);
    }

}
