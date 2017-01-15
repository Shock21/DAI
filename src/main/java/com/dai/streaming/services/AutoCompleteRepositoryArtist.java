package com.dai.streaming.services;

import com.dai.streaming.entity.Artist;
import com.dai.streaming.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Tiberiu on 1/15/2017.
 */
public interface AutoCompleteRepositoryArtist extends JpaRepository<Artist, Long> {

        Artist findByNameIgnoreCaseContaining(String name);

}
