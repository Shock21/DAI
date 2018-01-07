package com.dai.streaming.services;

import com.dai.streaming.dto.SongUploadDto;
import com.dai.streaming.entity.Artist;
import com.dai.streaming.entity.Song;
import org.springframework.web.multipart.MultipartFile;

public interface MusicStorageService {

    public void storeSong(MultipartFile file, SongUploadDto songUploadDto);

    public Song saveSong(Song song);

    public Artist saveArtist(Artist artist);
}
