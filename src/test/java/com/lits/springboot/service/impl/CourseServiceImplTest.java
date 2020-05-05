package com.lits.springboot.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.exceptions.*;
import com.lits.springboot.model.Course;
import com.lits.springboot.repository.CourseRepository;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.CourseService;
import com.lits.springboot.utils.ParseDataUtils;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    private CourseDto courseDto;
    private CourseService courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private ModelMapper modelMapper;

    @Before
    public void init() {
        courseService = new CourseServiceImpl(courseRepository, teacherRepository, modelMapper);
    }

    @Test
    public void create_course_courseDto() throws IOException {
        //Arrange
        CourseDto courseDto = ParseDataUtils
                .prepareData("unit/serviceImpl/course/create/positive_data.json", new TypeReference<>() {});

        Course course = ParseDataUtils
                .prepareData("unit/serviceImpl/course/create/positive_data.json", new TypeReference<>() {});
        Course expected = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/create/result.json", new TypeReference<>() {});

        when(courseRepository.save(course)).thenReturn(course);

        //Act
        CourseDto courseDto1 = courseService.create(courseDto);
        Course actual = modelMapper.map(courseDto, Course.class);

        //Assert
        Assert.assertEquals(expected, actual);
    }









    @Test
    public void create_nullCourseName_CreateException() {
        //Arrange
//        init();
        LocalDate now = LocalDate.now();
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName(null);
        courseDto.setStartDate(now);
        courseDto.setEndDate(now.plusMonths(2));
        //Act
        Throwable thrown = catchThrowable(() -> courseService.create(courseDto));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseCreateException.class);
    }

    @Test
    public void create_nullStartDate_CreateException() {
        //Arrange
//        init();
        LocalDate now = LocalDate.now();
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Java");
        courseDto.setStartDate(null);
        courseDto.setEndDate(now.plusMonths(2));
        //Act
        Throwable thrown = catchThrowable(() -> courseService.create(courseDto));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseCreateException.class);
    }

    @Test
    public void create_nullEndDate_CreateException() {
        //Arrange
//        init();
        LocalDate now = LocalDate.now();
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Java");
        courseDto.setStartDate(now);
        courseDto.setEndDate(null);
        //Act
        Throwable thrown = catchThrowable(() -> courseService.create(courseDto));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseCreateException.class);
    }

    @Test
    public void getOne_nullId_RequestException() {
        //Arrange
//        init();
        //Act
        Throwable thrown = catchThrowable(() -> courseService.getOne(null));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseRequestException.class);
    }

    @Test
    public void getOne_negativeId_NotFoundException() {
        //Arrange
//        init();
        //Act
        Throwable thrown = catchThrowable(() -> courseService.getOne(-1));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseNotFoundException.class);
    }

}