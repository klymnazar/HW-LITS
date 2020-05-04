package com.lits.springboot.service.impl;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.exceptions.TeacherCreateException;
import com.lits.springboot.exceptions.TeacherNotFoundException;
import com.lits.springboot.exceptions.TeacherRequestException;
import com.lits.springboot.model.Course;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.repository.CourseRepository;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository, ModelMapper modelMapper, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
        this.courseRepository = courseRepository;
    }

    @Override
    public TeacherDto update(Integer id, String newFirstName, String newLastName) {
        Teacher teacher = teacherRepository.findOneById(id);
        teacher.setFirstName(newFirstName);
        teacher.setLastName(newLastName);
        return modelMapper.map(teacherRepository.save(teacher), TeacherDto.class);
    }

    @Override
    public void delete(Integer id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public List<TeacherDto> getAll(String sortBy) {
        List<Teacher> teachers;
        if (("age").equals(sortBy)) {
            Sort sortByAge = Sort.by(sortBy).descending();
            teachers = teacherRepository.findAll(sortByAge);
        } else {
            teachers = teacherRepository.findAll();
        }
        return teachers.stream().map(teacher -> modelMapper.map(teacher, TeacherDto.class)).collect(Collectors.toList());
    }

    @Override
    public TeacherDto getOne(Integer id) {
        Teacher teacher;
        if (id == null) {
            throw new TeacherRequestException("Enter Teacher id");
//        } else if (id < 0) {
//            throw new TeacherNotFoundException(format("Teacher with id : %d doesn't exist", id));
        } else {
            teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(format("Teacher with id : %d doesn't exist", id)));
            return modelMapper.map(teacher, TeacherDto.class);
        }
    }

//    @Override
//    public TeacherDto create(String firstName, String lastName, Integer age) {
//        Teacher teacher;
//        if (firstName == null || lastName == null || age == null) {
//            throw new TeacherCreateException("New Teacher can not be created because all fields should not be null");
//        } else {
//            teacher = teacherRepository.save(new Teacher(firstName, lastName, age));
//            return modelMapper.map(teacher, TeacherDto.class);
//        }
//    }


    @Override
    public TeacherDto create(String firstName, String lastName, Integer age) {
                  return modelMapper.map(teacherRepository.save(new Teacher(firstName, lastName, age)), TeacherDto.class);
        }

    @Override
    public TeacherDto addCourseToTeacher(Integer teacherId, List<Integer> courseIds) {
        Teacher teacher = teacherRepository.findOneById(teacherId);
        List<Course> courses = new ArrayList<>();
        for (Integer courseId : courseIds) {
            courses.add(courseRepository.findOneById(courseId));
        }
        teacher.setCourseList(courses);
        teacherRepository.save(teacher);
        TeacherDto teacherDto = modelMapper.map(teacher, TeacherDto.class);
        teacherDto.setCourseIds(courseIds);
        return teacherDto;
    }

}
