package com.lits.springboot.controller;

import com.lits.springboot.dto.StudentDto;
import com.lits.springboot.service.CourseService;
import com.lits.springboot.service.StudentService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping("/students")
@Log
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("student/{id}")
    public StudentDto getById(@PathVariable(name = "id") Integer id) {
        log.info("Get student by id = " + id);
        return studentService.getOne(id);
    }

    @GetMapping("/students")
    public List<StudentDto> getAll() {
        log.info("Get all students");
        return studentService.getAll();
    }

    @PutMapping("/student/{id}")
    public StudentDto update(@PathVariable(name = "id") Integer id, @RequestBody @Valid StudentDto studentDto) {
        log.info("Update student by id = " + id);
        studentDto.setId(id);
        return studentService.update(studentDto);
    }

    @DeleteMapping("/student/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        log.info("Delete student by id= " + id);
        studentService.delete(id);
    }

    @PostMapping("/student")
    @ResponseStatus(value = HttpStatus.CREATED)
    public StudentDto create(@RequestBody @Valid StudentDto studentDto) {
        log.info("Create new student");
        return studentService.create(studentDto.getFirstName(), studentDto.getLastName(), studentDto.getAge());
    }

}

