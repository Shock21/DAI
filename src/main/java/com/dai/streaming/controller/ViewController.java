package com.dai.streaming.controller;

import com.dai.streaming.dto.SongDto;
import com.dai.streaming.dto.SongSearchDto;
import com.dai.streaming.dto.SongSearchRequestDto;
import com.dai.streaming.entity.Song;
import com.dai.streaming.services.AutoCompleteRepository;
import com.dai.streaming.utils.MultipartFileSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tvasile on 12/2/2016.
 */
@Controller
@RequestMapping(value = "/")
public class ViewController  {

    @Autowired
    AutoCompleteRepository autoComplete;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getArticle() {
        return "index";
    }

    @RequestMapping(value = "/audio", method = RequestMethod.GET)
    @ResponseBody
    public void playAudio(HttpServletRequest request, HttpServletResponse response) {
        String path = "E:\\MusicPlaylist\\Queen-We_Will_Rock_You.mp3";
        File file = new File(path);
        try {
            MultipartFileSender.fromFile(file).with(request).with(response).serveResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public SongSearchDto searchAutocomplete(@RequestBody @Valid SongSearchRequestDto songSearchRequest) {
        SongSearchDto songSearchDto = new SongSearchDto();
        SongDto songDto = new SongDto();
        List<SongDto> songInfo = new ArrayList<>();
        List<Song> songs = autoComplete.findByNameIgnoreCaseContaining(songSearchRequest.getContains());

        songs.forEach(song -> {
            songDto.setName(song.getName());
            songDto.setArtist(song.getArtist().getName());
            songDto.setDuration(song.getDuration());
            songInfo.add(songDto);
        });

        songSearchDto.setSearchParam(songSearchRequest.getContains());
        songSearchDto.setSongInfo(songInfo);

        return songSearchDto;
    }

}
