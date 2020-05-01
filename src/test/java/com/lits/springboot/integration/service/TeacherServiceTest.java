package com.lits.springboot.integration.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.TeacherService;
import com.lits.springboot.utils.ParseDataUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private ModelMapper modelMapper;

    @Test
    public void getAll_retrieveTeacher_returnTeachersDto() throws IOException, URISyntaxException {
        // Arrange
        URI fileName = getClass().getClassLoader()
                .getResource("integration/service/teacher/getAll/unsort/positive_data.sql").toURI();
        Files.lines(Paths.get(fileName)).forEach(line -> jdbcTemplate.update(line));
        List<Teacher> expected = ParseDataUtils
                .prepareData("integration/service/teacher/getAll/unsort/expected.json", new TypeReference<List<Teacher>>() {});

        // Act
        List<TeacherDto> teachersDto = teacherService.getAll(null);
        List<Teacher> actual = teachersDto.stream()
                .map(teacherDto -> modelMapper.map(teacherDto, Teacher.class)).collect(Collectors.toList());

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getAll_retrieveTeacher_returnSortTeachersDto() throws IOException, URISyntaxException {
        // Arrange
        String sortBy = "age";
        URI fileName = getClass().getClassLoader()
                .getResource("integration/service/teacher/getAll/sort/positive_data.sql").toURI();
        Files.lines(Paths.get(fileName)).forEach(line -> jdbcTemplate.update(line));
        List<Teacher> expected = ParseDataUtils
                .prepareData("integration/service/teacher/getAll/sort/expected.json", new TypeReference<List<Teacher>>() {});

        // Act
        List<TeacherDto> teachersDto = teacherService.getAll(sortBy);
        List<Teacher> actual = teachersDto.stream()
                .map(teacherDto -> modelMapper.map(teacherDto, Teacher.class)).collect(Collectors.toList());

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update_name_returnTeacherDto() throws IOException, URISyntaxException {
        // Arrange
        URI fileName = getClass().getClassLoader()
                .getResource("integration/service/teacher/update/positive_data.sql").toURI();
        Files.lines(Paths.get(fileName)).forEach(line -> jdbcTemplate.update(line));
        Teacher expected = ParseDataUtils
                .prepareData("integration/service/teacher/update/expected.json", new TypeReference<Teacher>() {});

        // Act
        TeacherDto teacherDto = teacherService.update(1, "John", "Smith");
        Teacher actual = modelMapper.map(teacherDto, Teacher.class);

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void delete_retrieveId_void() throws IOException, URISyntaxException {
        // Arrange
        int size;
        URI fileName = getClass().getClassLoader()
                .getResource("integration/service/teacher/delete/positive_data.sql").toURI();
        Files.lines(Paths.get(fileName)).forEach(line -> jdbcTemplate.update(line));

        // Act
        List<TeacherDto> teacherDtos = teacherService.getAll(null);
        size = teacherDtos.size();
        teacherService.delete(1);
        teacherDtos = teacherService.getAll(null);

        // Assert
        Assert.assertEquals(size - 1, teacherDtos.size());
    }

    @Test
    public void getOne_retrieveId_returnTeacherDto() throws IOException, URISyntaxException {
        // Arrange
        URI fileName = getClass().getClassLoader()
                .getResource("integration/service/teacher/getOne/positive_data.sql").toURI();
        Files.lines(Paths.get(fileName)).forEach(line -> jdbcTemplate.update(line));
        Teacher expected = ParseDataUtils
                .prepareData("integration/service/teacher/getOne/expected.json", new TypeReference<Teacher>() {});

        // Act
        TeacherDto teacherDto = teacherService.getOne(1);
        Teacher actual = modelMapper.map(teacherDto, Teacher.class);

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void create_retrieveTeacher_returnTeacherDto() throws IOException, URISyntaxException {
        // Arrange
        URI fileName = getClass().getClassLoader()
                .getResource("integration/service/teacher/create/positive_data.sql").toURI();
        Files.lines(Paths.get(fileName)).forEach(line -> jdbcTemplate.update(line));
        Teacher expected = ParseDataUtils
                .prepareData("integration/service/teacher/create/expected.json", new TypeReference<Teacher>() {});

        // Act
        TeacherDto teacherDto = teacherService.create("Piter", "Pen", 23);
        Teacher actual = modelMapper.map(teacherDto, Teacher.class);

        // Assert
        Assert.assertEquals(actual, expected);
    }

}