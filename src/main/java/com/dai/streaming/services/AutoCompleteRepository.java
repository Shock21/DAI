package com.dai.streaming.services;

import com.dai.streaming.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Tiberiu on 1/3/2017.
 */
public interface AutoCompleteRepository extends JpaRepository<Song, Long> {

    List<Song> findByNameIgnoreCaseContaining(String name);
}