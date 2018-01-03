package com.dai.streaming.dto;

import com.dai.streaming.entity.Artist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Tiberiu on 1/14/2017.
 */
public class SongDto {

    @Getter @Setter
    String songTitle;

    @Getter @Setter
    private Integer duration;

    @Getter @Setter
    private String artistName;

}
