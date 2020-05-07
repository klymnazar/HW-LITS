package com.lits.springboot.service;

import com.lits.springboot.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto getOne(Integer id);
    StudentDto update(Integer id, String newFirstName, String newLastName, Integer newAge);
    void delete(Integer id);
    List<StudentDto> getAll(String sortBy);
    StudentDto create(String firstName, String lastName, Integer age);
    List<StudentDto> getAll();

}
