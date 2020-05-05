package com.lits.springboot.service.impl;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.exceptions.*;
import com.lits.springboot.model.Course;
import com.lits.springboot.repository.CourseRepository;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto update(Integer id, String newCourseName) {
        Course course = modelMapper.map(courseRepository.findOneById(id), Course.class);
        course.setCourseName(newCourseName);
        return modelMapper.map(courseRepository.save(course), CourseDto.class);
    }

    @Override
    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseDto> getAll() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCoursesWithoutTeacher() {
        return null;
    }

    @Override
    public Course updateCourseTeacher(Integer courseId, Integer teacherId) {
        return null;
    }

    @Override
    public CourseDto addTeachersToCourse(Integer courseId, List<TeacherDto> teacherDtos) {
        CourseDto courseDto = getOne(courseId);
        courseDto.setTeacherDtos(teacherDtos);
        return modelMapper.map(courseRepository.save(modelMapper.map(courseDto, Course.class)), CourseDto.class);
    }

    @Override
    public List<CourseDto> getAllCourses(String type, Integer numberMonths) {
        LocalDate now = LocalDate.now();
        List<Course> courses;

        if (type != null) {
            courses = getCourseListByDurationType(type, now);
        } else if (numberMonths != null) {
            courses = courseRepository.findAllCoursesDurationMonths((numberMonths - 1) * 30, numberMonths * 30);
        } else {
            courses = courseRepository.findAllByOrderByStartDateAsc();
        }
        return courses.stream().map(course -> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
    }

    private List<Course> getCourseListByDurationType(String type, LocalDate now) {
        List<Course> courses;
        switch (type) {
            case "after":
                courses = courseRepository.findAllByStartDateAfterOrderByStartDate(now);
                break;
            case "before":
                courses = courseRepository.findAllByEndDateBeforeOrderByEndDate(now);
                break;
            case "active":
                courses = courseRepository.findAllByStartDateBeforeAndEndDateAfterOrderByStartDate(now, now);
                break;
            default:
                courses = courseRepository.findAllByOrderByStartDateAsc();
                break;
        }
        return courses;
    }

    @Override
    public CourseDto create(CourseDto courseDto) {
        if (courseDto.getCourseName() == null || courseDto.getStartDate() == null || courseDto.getEndDate() == null) {
            throw new CourseCreateException("New Course can not be created because all fields should not be null");
        } else {
            return modelMapper.map(courseRepository.save(modelMapper.map(courseDto, Course.class)), CourseDto.class);
        }
    }

    @Override
    public CourseDto getOne(Integer id) {
        Course course;
        if (id == null) {
            throw new CourseRequestException("Enter Course id");
        } else if (id < 0) {
            throw new CourseNotFoundException(format("Course with id : %d doesn't exist", id));
        } else {
            course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(format("Course with id : %d doesn't exist", id)));
            return modelMapper.map(course, CourseDto.class);
        }
    }

}