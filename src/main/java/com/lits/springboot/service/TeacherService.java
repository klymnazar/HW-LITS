package com.lits.springboot.service;

import com.lits.springboot.model.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher create(String firstName, String lastName, Integer age);

    Teacher getOne(Integer id);

    Teacher update(Integer id, String newFirstName, String newLastName);

    void delete(Integer id);

    List<Teacher> getAll(String sortBy);

}
