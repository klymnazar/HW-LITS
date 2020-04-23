package com.lits.springboot.service.impl;

import com.lits.springboot.model.Teacher;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.TeacherService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher create(String firstName, String lastName, Integer age) {
        return teacherRepository.save(new Teacher(firstName, lastName, age));
    }

    @Override
    public Teacher getOne(Integer id) {
        return teacherRepository.findOneById(id);
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

}
