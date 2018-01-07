package com.dai.streaming.services.impl;

import com.dai.streaming.constants.DownloadConstants;
import com.dai.streaming.dto.SongUploadDto;
import com.dai.streaming.entity.Artist;
import com.dai.streaming.entity.Song;
import com.dai.streaming.repository.AutoCompleteRepository;
import com.dai.streaming.repository.AutoCompleteRepositoryArtist;
import com.dai.streaming.services.MusicStorageService;
import com.dai.streaming.utils.StringsUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class MusicStorageServiceImpl implements MusicStorageService {

    @Autowired
    AutoCompleteRepositoryArtist autoCompleteRepositoryArtist;

    @Autowired
    AutoCompleteRepository autoCompleteRepository;

    AudioFile audioFile = null;

    @Override
    public void storeSong(MultipartFile file, SongUploadDto songUploadDto) {

        String pathName = DownloadConstants.LOCATION + StringsUtils.formatSongFileName(songUploadDto) + ".mp3";

        File songFile = new File(pathName);
        try {
            file.transferTo(songFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            audioFile = AudioFileIO.read(songFile);
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }

        Artist artist = autoCompleteRepositoryArtist.findByNameIgnoreCaseContaining(songUploadDto.getArtistName());

        if(artist == null) {
            artist = new Artist();
            artist.setName(songUploadDto.getArtistName());
            artist = saveArtist(artist);
        }

        Song newSong = new Song();
        newSong.setName(songUploadDto.getSongName());
        newSong.setArtist(artist);
        newSong.setDuration(audioFile.getAudioHeader().getTrackLength());
        newSong.setLocation(pathName);

        saveSong(newSong);

    }

    @Override
    @Transactional
    public Song saveSong(Song song) {
        return autoCompleteRepository.save(song);
    }

    @Override
    @Transactional
    public Artist saveArtist(Artist artist) {
        return autoCompleteRepositoryArtist.save(artist);
    }
}
