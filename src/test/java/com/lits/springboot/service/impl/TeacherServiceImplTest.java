package com.lits.springboot.service.impl;

import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.exceptions.TeacherCreateException;
import com.lits.springboot.exceptions.TeacherNotFoundException;
import com.lits.springboot.exceptions.TeacherRequestException;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.repository.CourseRepository;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.TeacherService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceImplTest {

    private TeacherDto teacherDto;
    private TeacherService teacherService;
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private CourseRepository courseRepository;

    @Before
    public void init() {
        teacherService = new TeacherServiceImpl(teacherRepository, modelMapper, courseRepository);
    }

    @Test
    public void update_newFirstName_teacherDto() {
        //Arrange
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Smith");
        teacher.setAge(21);
        teacher.setId(1);

        when(teacherRepository.findOneById(eq(1))).thenReturn(teacher);
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        //Act
        teacherDto = teacherService.update(1, "Donald", "Smith");

        //Assert
        Assert.assertEquals("Donald", teacherDto.getFirstName());
        Assert.assertEquals("Smith", teacherDto.getLastName());
        Assert.assertEquals(Integer.valueOf(21), teacherDto.getAge());
    }

    @Test
    public void update_newLastName_teacherDto() {
        //Arrange
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Smith");
        teacher.setAge(21);
        teacher.setId(1);

        when(teacherRepository.findOneById(eq(1))).thenReturn(teacher);
        when(teacherRepository.save(eq(teacher))).thenReturn(teacher);

        //Act
        teacherDto = teacherService.update(1, "John", "Duck");

        //Assert
        Assert.assertEquals("John", teacherDto.getFirstName());
        Assert.assertEquals("Duck", teacherDto.getLastName());
        Assert.assertEquals(Integer.valueOf(21), teacherDto.getAge());
    }

    @Test
    public void getOne_teacherId_teacher() {
        //Arrange
        Teacher teacher = new Teacher();

        teacher.setFirstName("John");
        teacher.setLastName("Smith");
        teacher.setAge(21);
        teacher.setId(1);

        when(teacherRepository.findById(eq(1))).thenReturn(Optional.of(teacher));

        //Act
        teacherDto = teacherService.getOne(1);

        //Assert
        Assert.assertEquals("John", teacherDto.getFirstName());
        Assert.assertEquals("Smith", teacherDto.getLastName());
        Assert.assertEquals(Integer.valueOf(21), teacherDto.getAge());
    }

//    @Test
//    public void create_teacher_teacherDto() {
//        //Arrange
//        String firstName = "Donald";
//        String lastName = "Duck";
//        Integer age = 30;
//        Integer id = 1;
//
//        when(teacherRepository.save(new Teacher(firstName, lastName, age))).thenReturn(new Teacher(id, firstName, lastName, age));
//
//        //Act
//        teacherDto = teacherService.create(firstName, lastName, age);
//
//        //Assert
//        Assert.assertEquals("Donald", teacherDto.getFirstName());
//        Assert.assertEquals("Duck", teacherDto.getLastName());
//        Assert.assertEquals(Integer.valueOf(30), teacherDto.getAge());
//    }


    @Test
    public void create_teacher_teacherDto() {
        //Arrange
        String firstName = "Donald";
        String lastName = "Duck";
        Integer age = 30;
        Integer id = 1;

        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setAge(age);
        teacher.setId(id);

        when(teacherRepository.save(new Teacher(firstName, lastName, age))).thenReturn(teacher);

        //Act
        teacherDto = teacherService.create(firstName, lastName, age);

        //Assert
        Assert.assertEquals(id, teacherDto.getId());
        Assert.assertEquals(firstName, teacherDto.getFirstName());
        Assert.assertEquals(lastName, teacherDto.getLastName());
        Assert.assertEquals(age, teacherDto.getAge());
    }

    @Test
    public void getAll_sortByAge_sortTeachers() {
        //Arrange
        String sortBy = "age";
        Teacher teacher = new Teacher();
        Teacher teacher1 = new Teacher();
        List<Teacher> teachers = new ArrayList<>();

        List<TeacherDto> teacherDtos;

        teacher.setFirstName("John");
        teacher.setLastName("Smith");
        teacher.setAge(21);
        teacher.setId(1);
        teachers.add(teacher);

        teacher1.setFirstName("Donald");
        teacher1.setLastName("Duck");
        teacher1.setAge(25);
        teacher1.setId(2);
        teachers.add(teacher1);

        Sort sortByAge = Sort.by(sortBy).descending();

        when(teacherRepository.findAll(sortByAge)).thenReturn(Arrays.asList(teacher));

        //Act
        teacherDtos = teacherService.getAll(sortBy);

        //Assert
        Assert.assertEquals(teachers.size(), 2);
    }

    @Test
    public void getOne_nullId_RequestException() {
        //Arrange
//        init();
        //Act
        Throwable thrown = catchThrowable(() -> teacherService.getOne(null));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(TeacherRequestException.class);
    }

    @Test
    public void getOne_negativeId_NotFoundException() {
        //Arrange
//        init();
        //Act
        Throwable thrown = catchThrowable(() -> teacherService.getOne(-1));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(TeacherNotFoundException.class);
    }

//    @Test
//    public void create_nullFirstName_CreateException() {
//        //Arrange
////        init();
//        //Act
//        Throwable thrown = catchThrowable(() -> teacherService.create(null, "Smith", 21));
//        //Assert
//        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
//    }
//
//    @Test
//    public void create_nullLastName_CreateException() {
//        //Arrange
////        init();
//        //Act
//        Throwable thrown = catchThrowable(() -> teacherService.create("John", null, 21));
//        //Assert
//        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
//    }
//
//    @Test
//    public void create_nullAge_CreateException() {
//        //Arrange
////        init();
//        //Act
//        Throwable thrown = catchThrowable(() -> teacherService.create("John", "Smith", null));
//        //Assert
//        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
//    }

}