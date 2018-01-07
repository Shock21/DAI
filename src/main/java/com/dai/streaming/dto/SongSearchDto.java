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
    private String searchParam;

    @Getter @Setter
    private String artistName;

    @Getter @Setter
    private Boolean artistSearch;

    @Getter @Setter
    private List<SongDto> songInfo;
}
