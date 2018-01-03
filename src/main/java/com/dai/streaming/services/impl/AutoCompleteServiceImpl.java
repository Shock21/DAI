package com.dai.streaming.services.impl;

import com.dai.streaming.dto.SongDto;
import com.dai.streaming.dto.SongSearchDto;
import com.dai.streaming.dto.SongSearchRequestDto;
import com.dai.streaming.entity.Artist;
import com.dai.streaming.entity.Song;
import com.dai.streaming.repository.AutoCompleteRepository;
import com.dai.streaming.repository.AutoCompleteRepositoryArtist;
import com.dai.streaming.services.AutoCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tvasile on 12/2/2016.
 */

@Service
public class AutoCompleteServiceImpl implements AutoCompleteService {

    @Autowired
    AutoCompleteRepository autoComplete;

    @Autowired
    AutoCompleteRepositoryArtist autoCompleteArtist;

    @Override
    public SongSearchDto search(SongSearchRequestDto songSearchRequestDto) {
        SongSearchDto songSearchDto = new SongSearchDto();
        Boolean artistPlaylist = false;
        List<SongDto> songInfo = new ArrayList<>();
        List<Song> songs = null;

        Artist artist = autoCompleteArtist.findByNameIgnoreCaseContaining(songSearchRequestDto.getContains());

        if(artist != null) {
            songs = autoComplete.findByArtist(artist);
            artistPlaylist = true;
            songSearchDto.setArtistName(artist.getName());
        } else {
            songs = autoComplete.findByNameIgnoreCaseStartsWith(songSearchRequestDto.getContains());
        }

        songs.forEach(song -> {
            SongDto songDto = new SongDto();
            songDto.setSongTitle(song.getName());
            songDto.setArtistName(song.getArtist().getName());
            songDto.setDuration(song.getDuration());
            songInfo.add(songDto);
        });

        songSearchDto.setSearchParam(songSearchRequestDto.getContains());
        songSearchDto.setArtistSearch(artistPlaylist);
        songSearchDto.setSongInfo(songInfo);

        return songSearchDto;
    }
}
