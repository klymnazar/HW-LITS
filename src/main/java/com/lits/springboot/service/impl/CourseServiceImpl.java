package com.lits.springboot.service.impl;

import com.lits.springboot.model.Course;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.repository.CourseRepository;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.CourseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course getOne(Integer id) {
        return courseRepository.findOneById(id);
    }

    @Override
    public Course update(Integer id, String newCourseName, Teacher newTeacher) {
        Course course = courseRepository.findOneById(id);
        course.setCourseName(newCourseName);
        return courseRepository.save(course);
    }

    @Override
    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
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
    public Course addTeachersToCourse(Integer courseId, List<Teacher> teachers) {
        Course course = courseRepository.findOneById(courseId);
        course.setTeacherList(teachers);
        return courseRepository.save(course);
    }


    @Override
    public List<Course> getAllCourses(String type, Integer numberMonths) {
        LocalDate now = LocalDate.now();
        List<Course> courses;

        if (type != null) {
            courses = getCourseListByDurationType(type, now);
        } else if (numberMonths != null) {
            courses = courseRepository.findAllCoursesDurationMonths((numberMonths - 1) * 30, numberMonths * 30);
        } else {
            courses = courseRepository.findAllByOrderByStartDateAsc();
        }
        return courses;
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

}
