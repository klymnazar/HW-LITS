package com.lits.springboot.controller;

import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.service.TeacherService;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher/{id}")
    public Teacher getById(@PathVariable(name = "id") Integer id) {
        return teacherService.getOne(id);
    }

    @PostMapping("/teacher")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Teacher create(@RequestBody TeacherDto teacherDto) {
        return teacherService.create(teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getAge());
    }

    @PutMapping("/teacher/{id}")
    public Teacher update(@PathVariable(name = "id") Integer id, @RequestBody TeacherDto teacherDto) {
        return teacherService.update(id, teacherDto.getFirstName(), teacherDto.getLastName());
    }

    @DeleteMapping("/teacher/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        teacherService.delete(id);
    }

    @GetMapping("/teachers")
    public List<Teacher> getAll(@RequestParam(name = "sortBy", required = false) String sortBy) {
        return teacherService.getAll(sortBy);
    }

}
