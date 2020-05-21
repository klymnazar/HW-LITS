package com.lits.springboot.repository;

import com.lits.springboot.model.Permission;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission, Integer> {
}
