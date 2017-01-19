package com.dai.streaming.services;

import com.dai.streaming.dto.SongSearchDto;
import com.dai.streaming.dto.SongSearchRequestDto;

/**
 * Created by tvasile on 12/2/2016.
 */
public interface AutoCompleteService {

    public SongSearchDto search(SongSearchRequestDto songSearchRequestDto);
}
