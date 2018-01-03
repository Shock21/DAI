package com.dai.streaming.services.impl;

import com.dai.streaming.dto.CurrentSong;
import com.dai.streaming.repository.AutoCompleteRepository;
import com.dai.streaming.services.LyricsService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;


@Service
public class LyricsServiceImpl implements LyricsService {

    private static final String endpoint = "http://api.musixmatch.com/ws/1.1/matcher.lyrics.get?q_track=";
    private RestTemplate rest;
    private HttpHeaders header;
    private URI url;
    private JSONObject jsonObject;

    @Autowired
    AutoCompleteRepository autoCompleteRepository;

    @Override
    public String getLyrics(CurrentSong currentSong) {
        rest = new RestTemplate();
        header = new HttpHeaders();

        header.add("apikey", "2a53d55cb64d9acf0f0891d42715d0b6");

        HttpEntity<String> requestEntity = new HttpEntity<String>("", header);

        try {
            String urlString = endpoint + URLEncoder.encode(currentSong.getSongTitle(), "UTF-8") + "&q_artist=" + URLEncoder.encode(currentSong.getArtistName(), "UTF-8") + "&apikey=2a53d55cb64d9acf0f0891d42715d0b6";
            url = new URI(urlString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);

        JSONParser jsonParser = new JSONParser();
        try {
            jsonObject = (JSONObject) jsonParser.parse(response.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject.get("lyrics_body").toString();
    }
}
