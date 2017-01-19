package com.dai.streaming.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Tiberiu on 1/18/2017.
 */
@Entity
@Table(name = "User")
public class User {

    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column @Getter @Setter
    private String username;

    @Column @Getter @Setter
    private String password;

    @Transient @Getter @Setter
    private String passwordConfirm;
}
