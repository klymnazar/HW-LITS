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

    @GetMapping("/courses/{id}")
    public CourseDto getById(@PathVariable(name = "id") Integer id) {
        return courseService.getOne(id);
    }

    @PostMapping("/courses")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CourseDto create(@RequestBody CourseDto courseDto) {
        return courseService.create(courseDto);
    }

    @PutMapping("/courses/{id}")
    public CourseDto update(@PathVariable(name = "id") Integer id, @RequestBody CourseDto courseDto) {
        return courseService.update(id, courseDto.getCourseName());
    }

    @DeleteMapping("/courses/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        courseService.delete(id);
    }

//    @GetMapping("/allCourses")
//    public List<CourseDto> getAll() {
//        return courseService.getAll();
//    }


    @PutMapping("/courses/{id}/teachers")
    public CourseDto addTeachers(@PathVariable(name = "id") Integer courseId, @RequestBody CourseDto courseDto) {
        List<Integer> teacherIds = new ArrayList<>();
        for (Integer teacherId : courseDto.getTeacherIds()) {
            teacherIds.add(teacherService.getOne(teacherId).getId());
        }
        return courseService.addTeachersToCourse(courseId, teacherIds);
    }




    @GetMapping("/courses")
    public List<CourseDto> getAllCourses(@RequestParam(name = "durationType", required = false) String durationType,
                                         @RequestParam(name = "durationMonths", required = false) Integer durationMonths) {
        return courseService.getAllCourses(durationType, durationMonths);
    }

}
