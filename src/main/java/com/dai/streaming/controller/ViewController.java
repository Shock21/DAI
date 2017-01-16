package com.dai.streaming.controller;

import com.dai.streaming.dto.SongDto;
import com.dai.streaming.dto.SongSearchDto;
import com.dai.streaming.dto.SongSearchRequestDto;
import com.dai.streaming.entity.Artist;
import com.dai.streaming.entity.Song;
import com.dai.streaming.services.AutoCompleteRepository;
import com.dai.streaming.services.AutoCompleteRepositoryArtist;
import com.dai.streaming.utils.MultipartFileSender;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    AutoCompleteRepositoryArtist autoCompleteArtist;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getArticle() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpStatus makeRegister() {
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/audio", method = RequestMethod.GET)
    @ResponseBody
    public void playAudio(HttpServletRequest request, HttpServletResponse response, @RequestParam("song") String songName) {
        Song song = autoComplete.findByName(songName);
        System.out.println(" ---------- " + songName + " ---------- ");
        File file = new File(song.getLocation());
        try {
            MultipartFileSender.fromFile(file).with(request).with(response).serveResource();
        } catch (Exception e) {

        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public SongSearchDto searchAutocomplete(@RequestBody @Valid SongSearchRequestDto songSearchRequest) {
        SongSearchDto songSearchDto = new SongSearchDto();
        Boolean artistPlaylist = false;
        List<SongDto> songInfo = new ArrayList<>();
        List<Song> songs = null;

        Artist artist = autoCompleteArtist.findByNameIgnoreCaseContaining(songSearchRequest.getContains());

        if(artist != null) {
            songs = autoComplete.findByArtist(artist);
            artistPlaylist = true;
            songSearchDto.setArtistName(artist.getName());
        } else {
            songs = autoComplete.findByNameIgnoreCaseStartsWith(songSearchRequest.getContains());
        }

        songs.forEach(song -> {
            SongDto songDto = new SongDto();
            songDto.setName(song.getName());
            songDto.setArtist(song.getArtist().getName());
            songDto.setDuration(song.getDuration());
            songInfo.add(songDto);
        });

        songSearchDto.setSearchParam(songSearchRequest.getContains());
        songSearchDto.setArtistSearch(artistPlaylist);
        songSearchDto.setSongInfo(songInfo);

        return songSearchDto;
    }

}
