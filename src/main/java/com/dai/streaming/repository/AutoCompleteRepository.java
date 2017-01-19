package com.dai.streaming.repository;

import com.dai.streaming.entity.Artist;
import com.dai.streaming.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Tiberiu on 1/3/2017.
 */
public interface AutoCompleteRepository extends JpaRepository<Song, Long> {

    List<Song> findByNameIgnoreCaseStartsWith(String name);

    List<Song> findByArtist(Artist artist);

    Song findByName(String name);
}
