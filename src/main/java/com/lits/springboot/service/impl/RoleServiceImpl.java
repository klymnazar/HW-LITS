package com.lits.springboot.service.impl;

import com.lits.springboot.dto.RoleDto;
import com.lits.springboot.model.Role;
import com.lits.springboot.repository.RoleRepository;
import com.lits.springboot.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDto create(RoleDto roleDto) {
        Role role;
        role = roleRepository.save(modelMapper.map(roleDto, Role.class));
        return modelMapper.map(role, RoleDto.class);
    }
}
