package com.lits.springboot.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
//    private String role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
    @Fetch(FetchMode.SELECT)
    private List<Role> roles = new ArrayList<>();

}