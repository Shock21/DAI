package com.dai.streaming.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

@XmlRootElement(name = "GetLyricResult")
public class GetLyricsResult {

    @XmlElement(name = "Id")
    private int id;

    @XmlElement(name = "LyricsUrl")
    private URI lyricsUrl;

    @XmlElement(name = "ArtistUrl")
    private URI artistUrl;

    @XmlElement(name = "Artist")
    private String artist;

    @XmlElement(name = "Title")
    private String title;

    @XmlElement(name = "Lyric")
    private String lyric;
}
