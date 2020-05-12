package com.lits.springboot.service;

import com.lits.springboot.dto.UserDto;

public interface UserService {

    UserDto getByUsername(String username);

    Integer create(UserDto userDto);

}
