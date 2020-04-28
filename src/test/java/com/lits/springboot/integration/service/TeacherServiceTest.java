package com.lits.springboot.integration.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.TeacherService;
import com.lits.springboot.service.impl.TeacherServiceImpl;
import com.lits.springboot.utils.ParseDataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherServiceTest {

//    @Autowired
    private TeacherService teacherService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @MockBean
    private TeacherRepository teacherRepository;
    @InjectMocks
    private ModelMapper modelMapper;

//    @Before
    public void init() {
        teacherService = new TeacherServiceImpl(teacherRepository, modelMapper);
    }

    @Test
    public void getAll_retrieveTeacher_returnTeachers() throws IOException, URISyntaxException {
        // Arrange

        init();

        String sortBy = "age";
        Sort sortByAge = Sort.by(sortBy).descending();

//        String fileName = getClass().getClassLoader().getResource("integration/service/teacher/getAll/positive_data.sql").getFile();

        URI fileName = getClass().getClassLoader().getResource("integration/service/teacher/getAll/positive_data.sql").toURI();

        List<Teacher> expected = ParseDataUtils.prepareData("integration/service/teacher/getAll/expected.json", new TypeReference<List<Teacher>>() {
        });

        Files.lines(Paths.get(fileName)).forEach(line -> jdbcTemplate.update(line));

        // Act
//        List<TeacherDto> teachersDto = teacherService.getAll(sortBy);
        List<TeacherDto> teachersDto = teacherService.getAll(null);
        List<Teacher> actual = teachersDto.stream().map(teacherDto -> modelMapper.map(teacherDto, Teacher.class)).collect(Collectors.toList());

        // Assert
        Assert.assertEquals(teachersDto.size(), expected.size());

        Assert.assertEquals(actual, expected);

    }

//    @Test
//    public void getAll_retrieveTeacher_returnTeachers() throws IOException {
//        // Arrange
//        String sortBy = "age";
//        Sort sortByAge = Sort.by(sortBy).descending();
//
////        String fileName = getClass().getClassLoader().getResource("integration/service/teacher/getAll/positive_data.sql").getFile();
//
//        List<Teacher> expected = ParseDataUtils.prepareData("integration/service/teacher/getAll/expected.json", new TypeReference<List<Teacher>>() {
//        });
//
//        when(teacherRepository.findAll(sortByAge)).thenReturn(expected);
//
////        Files.lines(Paths.get(fileName)).forEach(line -> jdbcTemplate.update(line));
//
//        // Act
//
////        List<TeacherDto> teachersDto = teacherService.getAll(sortBy);
//
//        List<TeacherDto> teachersDto = teacherService.getAll(sortBy);
//        List<Teacher> actual = teachersDto.stream().map(teacherDto -> modelMapper.map(teacherDto, Teacher.class)).collect(Collectors.toList());
//
//        // Assert
//        Assert.assertEquals(teachersDto.size(), expected.size());
//
//        Assert.assertEquals(actual, expected);
//
//    }






}


//    private TeacherDto teacherDto;
//    private TeacherService teacherService;
//    @Mock
//    private TeacherRepository teacherRepository;
//
//    @InjectMocks
//    private ModelMapper modelMapper;
//
//    @Before
//    public void init() {
//        teacherService = new TeacherServiceImpl(teacherRepository, modelMapper);
//    }
//
//    @Test
//    public void update_newFirstName_teacherDto() throws IOException {
//        //Arrange
//        Teacher teacher = ParseDataUtils.prepareData("unit/serviceImpl/teacher/update/firstName/positive_data.json", new TypeReference<>() {
//        });
//        Teacher expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/update/firstName/result.json", new TypeReference<>() {
//        });
//
//        when(teacherRepository.findOneById(eq(1))).thenReturn(teacher);
//        when(teacherRepository.save(teacher)).thenReturn(teacher);
//
//        //Act
//        teacherDto = teacherService.update(1, "Donald", "Smith");
//        Teacher actual = modelMapper.map(teacherDto, Teacher.class);
//
//        //Assert
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void update_newLastName_teacherDto() throws IOException {
//        //Arrange
//        Teacher teacher = ParseDataUtils.prepareData("unit/serviceImpl/teacher/update/lastName/positive_data.json", new TypeReference<>() {
//        });
//        Teacher expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/update/lastName/result.json", new TypeReference<>() {
//        });
//
//        when(teacherRepository.findOneById(eq(1))).thenReturn(teacher);
//        when(teacherRepository.save(teacher)).thenReturn(teacher);
//
//        //Act
//        teacherDto = teacherService.update(1, "John", "Duck");
//        Teacher actual = modelMapper.map(teacherDto, Teacher.class);
//
//        //Assert
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getOne_teacherId_teacher() throws IOException {
//        //Arrange
//        Teacher teacher = ParseDataUtils.prepareData("unit/serviceImpl/teacher/getOne/positive_data.json", new TypeReference<>() {
//        });
//        Teacher expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/getOne/result.json", new TypeReference<>() {
//        });
//
//        when(teacherRepository.findById(eq(1))).thenReturn(Optional.of(teacher));
//
//        //Act
//        teacherDto = teacherService.getOne(1);
//        Teacher actual = modelMapper.map(teacherDto, Teacher.class);
//
//        //Assert
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void create_teacher_teacherDto() throws IOException {
//        //Arrange
//        Teacher teacher = ParseDataUtils.prepareData("unit/serviceImpl/teacher/create/positive_data.json", new TypeReference<>() {
//        });
//        Teacher expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/create/result.json", new TypeReference<>() {
//        });
//
//        when(teacherRepository.save(new Teacher(teacher.getFirstName(), teacher.getLastName(), teacher.getAge()))).thenReturn(new Teacher(teacher.getFirstName(), teacher.getLastName(), teacher.getAge()));
//
//        //Act
//        teacherDto = teacherService.create(teacher.getFirstName(), teacher.getLastName(), teacher.getAge());
//        Teacher actual = modelMapper.map(teacherDto, Teacher.class);
//
//        //Assert
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getAll_sortByAge_sortTeachers() throws IOException {
//        //Arrange
//        List<Teacher> teachers = ParseDataUtils.prepareData("unit/serviceImpl/teacher/getAll/positive_data.json", new TypeReference<>() {
//        });
//        List<Teacher> expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/getAll/result.json", new TypeReference<>() {
//        });
//
//        String sortBy = "age";
//        Sort sortByAge = Sort.by(sortBy).descending();
//        when(teacherRepository.findAll(sortByAge)).thenReturn(teachers);
//
//        //Act
//        List<TeacherDto> actual = teacherService.getAll(sortBy);
//
//        //Assert
//        Assert.assertEquals(expected.size(), actual.size());
//    }
//
//    @Test
//    public void delete() throws IOException {
//        //Arrange
//        doNothing().when(teacherRepository).deleteById(eq(1));
//
//        //Act
//        teacherService.delete(1);
//
//        //Assert
//        verify(teacherRepository, times(1)).deleteById(eq(1));
//    }
//
//
//    @Test
//    public void getOne_nullId_RequestException() {
//        //Arrange
//        //Act
//        Throwable thrown = catchThrowable(() -> teacherService.getOne(null));
//        //Assert
//        Assertions.assertThat(thrown).isInstanceOf(TeacherRequestException.class);
//    }
//
//    @Test
//    public void getOne_negativeId_NotFoundException() {
//        //Arrange
//        //Act
//        Throwable thrown = catchThrowable(() -> teacherService.getOne(-1));
//        //Assert
//        Assertions.assertThat(thrown).isInstanceOf(TeacherNotFoundException.class);
//    }
//
//    @Test
//    public void create_nullFirstName_CreateException() {
//        //Arrange
//        //Act
//        Throwable thrown = catchThrowable(() -> teacherService.create(null, "Smith", 21));
//        //Assert
//        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
//    }
//
//    @Test
//    public void create_nullLastName_CreateException() {
//        //Arrange
//        //Act
//        Throwable thrown = catchThrowable(() -> teacherService.create("John", null, 21));
//        //Assert
//        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
//    }
//
//    @Test
//    public void create_nullAge_CreateException() {
//        //Arrange
//        //Act
//        Throwable thrown = catchThrowable(() -> teacherService.create("John", "Smith", null));
//        //Assert
//        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
//    }
//
//
//}