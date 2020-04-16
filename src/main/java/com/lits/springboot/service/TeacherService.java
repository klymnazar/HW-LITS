package com.lits.springboot.service;

import com.lits.springboot.model.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher getOne(Integer id);

    Teacher update(Integer id, String newFirstName, String newLastName);

    void delete(Integer id);

    List<Teacher> getAll(String sortBy);

    Teacher create(String firstName, String lastName, Integer age);

}
