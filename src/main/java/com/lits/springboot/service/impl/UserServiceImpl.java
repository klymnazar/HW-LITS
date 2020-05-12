package com.lits.springboot.service.impl;

import com.lits.springboot.dto.UserDto;
import com.lits.springboot.exceptions.user.UsernameNotFoundException;
import com.lits.springboot.model.User;
import com.lits.springboot.repository.UserRepository;
import com.lits.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(format("User with username : %s doesn't exist", username)));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Integer create(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user).getId();
    }

}
