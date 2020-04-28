package com.lits.springboot.controller;

import com.lits.springboot.dto.TeacherDto;
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
    public TeacherDto getById(@PathVariable(name = "id") Integer id) {
        return teacherService.getOne(id);
    }

    @GetMapping("/teachers")
    public List<TeacherDto> getAll(@RequestParam(name = "sortBy", required = false) String sortBy) {
        return teacherService.getAll(sortBy);
    }

//    @GetMapping("/teacherBy")
//    public TeacherDto getBy(@RequestParam(name = "id", required = false) Integer id) {
//        return teacherService.getOne(id);
//    }

    @PutMapping("/teacher/{id}")
    public TeacherDto update(@PathVariable(name = "id") Integer id, @RequestBody TeacherDto teacherDto) {
        return teacherService.update(id, teacherDto.getFirstName(), teacherDto.getLastName());
    }

    @DeleteMapping("/teacher/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        teacherService.delete(id);
    }

    @PostMapping("/teacher")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeacherDto create(@RequestBody TeacherDto teacherDto) {
        return teacherService.create(teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getAge());
    }

}

