package com.lits.springboot.controller;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.service.CourseService;
import com.lits.springboot.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping("/course/{id}")
    public CourseDto getById(@PathVariable(name = "id") Integer id) {
        return courseService.getOne(id);
    }

    @PostMapping("/course")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CourseDto create(@RequestBody CourseDto courseDto) {
        return courseService.create(courseDto);
    }

    @PutMapping("/course/{id}")
    public CourseDto update(@PathVariable(name = "id") Integer id, @RequestBody CourseDto courseDto) {
        return courseService.update(id, courseDto.getCourseName(), teacherService.getOne(courseDto.getTeacherId()));
    }

    @DeleteMapping("/course/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        courseService.delete(id);
    }

    @GetMapping("/allCourses")
    public List<CourseDto> getAll() {
        return courseService.getAll();
    }

    @PutMapping("/course/{id}/teachers")
    public CourseDto addTeachers(@PathVariable(name = "id") Integer courseId, @RequestBody CourseDto courseDto) {
        List<TeacherDto> teachers = new ArrayList<>();
        for (Integer teacherId : courseDto.getTeacherIds()) {
            teachers.add(teacherService.getOne(teacherId));
        }
        return courseService.addTeachersToCourse(courseId, teachers);
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourses(@RequestParam(name = "durationType", required = false) String durationType,
                                         @RequestParam(name = "durationMonths", required = false) Integer durationMonths) {
        return courseService.getAllCourses(durationType, durationMonths);
    }

}
