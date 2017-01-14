package com.dai.streaming.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Tiberiu on 1/3/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongSearchDto {

    @Getter @Setter
    String searchParam;

    @Getter @Setter
    List<SongDto> songInfo;
}
