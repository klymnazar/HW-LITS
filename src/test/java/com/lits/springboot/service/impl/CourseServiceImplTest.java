package com.lits.springboot.service.impl;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.exceptions.*;
import com.lits.springboot.repository.CourseRepository;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.CourseService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

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