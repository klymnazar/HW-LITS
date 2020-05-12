package com.lits.springboot.controller;

import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.service.TeacherService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher/{id}")
    public TeacherDto getById(@PathVariable(name = "id") Integer id) {
        log.info("Get teacher by id = " + id);
        return teacherService.getOne(id);
    }

    @GetMapping("/teacher")
    public TeacherDto getByPhone(@RequestParam(name = "phone", required = false) String phone) {
        log.info("Get teacher by phone = " + phone);
        return teacherService.getOneByPhone(phone);
    }

    @GetMapping("/teachers")
    public List<TeacherDto> getAll(@RequestParam(name = "sortBy", required = false) String sortBy) {
        List<TeacherDto> teacherDtos;
        if (("age").equals(sortBy)) {
            teacherDtos = teacherService.getAll(sortBy);
        } else {
            teacherDtos = teacherService.getAll();
        }
        log.info("Get all teachers");
        return teacherDtos;
    }

    @PutMapping("/teacher/{id}")
    public TeacherDto update(@PathVariable(name = "id") Integer id, @RequestBody TeacherDto teacherDto) {
        log.info("Update teacher by id = " + id);
        return teacherService.update(id, teacherDto.getFirstName(), teacherDto.getLastName());
    }

    @DeleteMapping("/teacher/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable(name = "id") Integer id) {
        log.info("Delete teacher by id= " + id);
        teacherService.delete(id);
    }

    @PostMapping("/teacher")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeacherDto create(@RequestBody TeacherDto teacherDto) {
        log.info("Create new teacher");
        return teacherService.create(teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getAge());
    }

}

