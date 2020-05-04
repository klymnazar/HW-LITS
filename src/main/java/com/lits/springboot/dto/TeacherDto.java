package com.lits.springboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeacherDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Integer> courseIds;
}
