package com.lits.springboot.dto;

import com.lits.springboot.model.Role;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private Integer id;
    @NotNull(message = "User can not be created/updated because username is null")
    private String username;
    @NotNull(message = "User can not be created/updated because password is null")
    private String password;
    @NotNull(message = "User can not be created/updated because role is null")
//    private String role;
    private List<Role> roles = new ArrayList<>();
}
