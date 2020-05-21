package com.lits.springboot.controller;

import com.lits.springboot.dto.RoleDto;
import com.lits.springboot.exceptions.role.RoleNotFoundException;
import com.lits.springboot.model.Permission;
import com.lits.springboot.model.Role;
import com.lits.springboot.repository.PermissionRepository;
import com.lits.springboot.repository.RoleRepository;
import com.lits.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

//    @PostMapping
//    public Role save(@RequestBody Role role) {
//        return roleRepository.save(role);
//    }

    @PostMapping
    public RoleDto create(@RequestBody RoleDto roleDto) {
        return roleService.create(roleDto);
    }


    @PutMapping("/{id}")
    public Role putPermissions(@PathVariable(name = "id") Integer id, @RequestBody List<String> permissions) {
        return roleRepository.findById(id).map(role -> {
            role.setPermissions(permissions.stream().map(e -> new Permission(e, id)).map(e -> permissionRepository.save(e)).collect(Collectors.toList()));
            return role;
        }).map(role -> roleRepository.save(role)).orElseThrow(() -> new RoleNotFoundException(format("Role with id : %d doesn't exist", id)));
    }
}
