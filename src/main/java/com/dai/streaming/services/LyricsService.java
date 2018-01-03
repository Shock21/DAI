package com.dai.streaming.services;

import com.dai.streaming.dto.CurrentSong;

public interface LyricsService {

    public String getLyrics(CurrentSong currentSong);
}
