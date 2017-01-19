package com.dai.streaming.services.impl;

import com.dai.streaming.entity.Song;
import com.dai.streaming.repository.AutoCompleteRepository;
import com.dai.streaming.services.MusicService;
import com.dai.streaming.utils.MultipartFileSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by Tiberiu on 1/19/2017.
 */

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    AutoCompleteRepository autoComplete;

    @Override
    public void play(String songName, HttpServletRequest request, HttpServletResponse response) {
        Song song = autoComplete.findByName(songName);
        System.out.println(" ---------- " + songName + " ---------- ");
        File file = new File(song.getLocation());
        try {
            MultipartFileSender.fromFile(file).with(request).with(response).serveResource();
        } catch (Exception e) {}
    }

}
