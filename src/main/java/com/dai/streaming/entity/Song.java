package com.dai.streaming.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Tiberiu on 1/3/2017.
 */
@Entity
@Table(name = "Song")
public class Song {

    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column @Getter @Setter
    String name;

    @Getter @Setter
    @ManyToOne(targetEntity = Artist.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Column @Getter @Setter
    private Integer duration;

    @Column @Getter @Setter
    String location;


}
