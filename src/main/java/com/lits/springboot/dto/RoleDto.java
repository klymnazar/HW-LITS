package com.lits.springboot.dto;

import com.lits.springboot.model.Permission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoleDto {
    private Integer id;
    private String name;
    private List<Permission> permissions = new ArrayList<>();
    private Integer userId;
}
