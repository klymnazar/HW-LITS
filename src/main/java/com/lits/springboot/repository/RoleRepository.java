package com.lits.springboot.repository;

import com.lits.springboot.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findAll();
    List<Role> findAllByIdIn(List<Integer> ids);
}
