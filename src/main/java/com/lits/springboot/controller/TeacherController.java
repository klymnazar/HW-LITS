package com.lits.springboot.controller;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.exceptions.TeacherCreateException;
//import com.lits.springboot.model.Teacher;
import com.lits.springboot.service.CourseService;
import com.lits.springboot.service.TeacherService;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeacherController {

    private final TeacherService teacherService;
    private final CourseService courseService;

    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("/teachers/{id}")
    public TeacherDto getById(@PathVariable(name = "id") Integer id) {
        return teacherService.getOne(id);
    }

    @PutMapping("/teachers/{id}")
    public TeacherDto update(@PathVariable(name = "id") Integer id, @RequestBody TeacherDto teacherDto) {
        return teacherService.update(id, teacherDto.getFirstName(), teacherDto.getLastName());
    }

    @DeleteMapping("/teachers/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        teacherService.delete(id);
    }

    @GetMapping("/teachers")
    public List<TeacherDto> getAll(@RequestParam(name = "sortBy", required = false) String sortBy) {
        return teacherService.getAll(sortBy);
    }

    @GetMapping("/teacherBy")
    public TeacherDto getBy(@RequestParam(name = "id", required = false) Integer id) {
        return teacherService.getOne(id);
    }

//    @PostMapping("/teachers")
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public TeacherDto create(@RequestBody TeacherDto teacherDto) {
//        return teacherService.create(teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getAge());
//    }

    @PostMapping("/teachers")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeacherDto create(@RequestBody TeacherDto teacherDto) {
        if (teacherDto.getFirstName() == null || teacherDto.getLastName() == null || teacherDto.getAge() == null) {
            throw new TeacherCreateException("New Teacher can not be created because all fields should not be null");
        } else {
            return teacherService.create(teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getAge());
        }
    }

    @PutMapping("/teachers/{id}/courses")
    public TeacherDto addCourses(@PathVariable(name = "id") Integer teacherId, @RequestBody TeacherDto teacherDto) {
        List<Integer> courseIds = new ArrayList<>();
        for (Integer courseId : teacherDto.getCourseIds()) {
            courseIds.add(courseService.getOne(courseId).getId());
        }
        return teacherService.addCourseToTeacher(teacherId, courseIds);
    }


}

