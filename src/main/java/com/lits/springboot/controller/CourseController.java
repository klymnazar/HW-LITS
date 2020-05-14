package com.lits.springboot.controller;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.service.CourseService;
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

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping("/course/{id}")
    public CourseDto getById(@PathVariable(name = "id") Integer id) {
        log.info("Get course by id = " + id);
        return courseService.getOne(id);
    }

    @PostMapping("/course")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CourseDto create(@RequestBody CourseDto courseDto) {
        log.info("Create new course");
        return courseService.create(courseDto);
    }

    @PutMapping("/course/{id}")
    public CourseDto update(@PathVariable(name = "id") Integer id, @RequestBody CourseDto courseDto) {
        log.info("Update course by id = " + id);
        return courseService.update(id, courseDto.getCourseName());
    }

    @DeleteMapping("/course/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        log.info("Delete course by id= " + id);
        courseService.delete(id);
    }

    @GetMapping("/allCourses")
    public List<CourseDto> getAll() {
        log.info("Get all courses");
        return courseService.getAll();
    }

    @PutMapping("/course/{id}/teachers")
    public CourseDto addTeachers(@PathVariable(name = "id") Integer courseId, @RequestBody CourseDto courseDto) {
        List<TeacherDto> teacherDtos = new ArrayList<>();
        for (Integer teacherId : courseDto.getTeacherIds()) {
            teacherDtos.add(teacherService.getOne(teacherId));
        }
        log.info("Add teachers to course");
        return courseService.addTeachersToCourse(courseId, teacherDtos);
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourses(@RequestParam(name = "durationType", required = false) String durationType,
                                         @RequestParam(name = "durationMonths", required = false) Integer durationMonths) {
        log.info("Get all courses");
        return courseService.getAllCourses(durationType, durationMonths);
    }

}
