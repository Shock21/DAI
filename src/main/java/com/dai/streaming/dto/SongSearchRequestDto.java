package com.dai.streaming.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tiberiu on 1/3/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SongSearchRequestDto {

    @Getter @Setter
    String contains;
}
