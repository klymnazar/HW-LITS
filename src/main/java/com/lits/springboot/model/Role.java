package com.lits.springboot.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class Role {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "roleId")
//    @Fetch(FetchMode.SELECT)
    private List<Permission> permissions = new ArrayList<>();

//    @Column(nullable = false)
    private Integer userId;
}
