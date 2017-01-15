package com.dai.streaming.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Tiberiu on 1/3/2017.
 */
@Entity
@Table(name = "Artist")
public class Artist {

    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column @Getter @Setter
    String name;

}
