package com.lits.springboot.service.impl;

import com.lits.springboot.exceptions.TeacherCreateException;
import com.lits.springboot.exceptions.TeacherNotFoundException;
import com.lits.springboot.exceptions.TeacherRequestException;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.TeacherService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher update(Integer id, String newFirstName, String newLastName) {
        Teacher teacher = teacherRepository.findOneById(id);
        teacher.setFirstName(newFirstName);
        teacher.setLastName(newLastName);
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(Integer id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public List<Teacher> getAll(String sortBy) {
        List<Teacher> teachers;
        if (("age").equals(sortBy)) {
            Sort sortByAge = Sort.by(sortBy).descending();
            teachers = teacherRepository.findAll(sortByAge);
        } else {
            teachers = teacherRepository.findAll();
        }
        return teachers;
    }

    @Override
    public Teacher getOne(Integer id) {
        if (id == null) {
            throw new TeacherRequestException("Enter Teacher id");
        } else if (id < 0) {
            throw new TeacherNotFoundException(format("Teacher with id : %d doesn't exist", id));
        } else {
            return teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(format("Teacher with id : %d doesn't exist", id)));
        }
    }

    @Override
    public Teacher create(String firstName, String lastName, Integer age) {
        if (firstName == null || lastName == null || age == null) {
            throw new TeacherCreateException("New Teacher can not be created because all fields should not be null");
        } else {
            return teacherRepository.save(new Teacher(firstName, lastName, age));
        }
    }

}
