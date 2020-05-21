package com.lits.springboot.controller;

import com.lits.springboot.model.Permissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @GetMapping
    public List<String> findAll() {
        return Arrays.asList(Permissions.values()).stream().map(e -> e.getCode()).collect(Collectors.toList());
    }
}
