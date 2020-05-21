package com.lits.springboot.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "permission")
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
    private Integer roleId;

    public Permission() {
    }

    public Permission(String name, Integer roleId) {
        this.name = name;
        this.roleId = roleId;
    }

}
