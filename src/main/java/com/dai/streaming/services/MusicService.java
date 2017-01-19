package com.dai.streaming.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tiberiu on 1/19/2017.
 */
public interface MusicService {

    public void play(String songName, HttpServletRequest request, HttpServletResponse response);

}
