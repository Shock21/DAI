package com.dai.streaming.services;

import org.json.simple.JSONObject;

public interface LyricsService {

    public JSONObject getLyrics(String songTitle, String artistName);
}
