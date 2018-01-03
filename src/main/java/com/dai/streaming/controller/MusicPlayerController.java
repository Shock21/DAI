package com.dai.streaming.controller;

import com.dai.streaming.dto.CurrentSong;
import com.dai.streaming.dto.SongSearchDto;
import com.dai.streaming.dto.SongSearchRequestDto;
import com.dai.streaming.entity.User;
import com.dai.streaming.services.AutoCompleteService;
import com.dai.streaming.services.LyricsService;
import com.dai.streaming.services.MusicService;
import com.dai.streaming.services.UserService;
import com.dai.streaming.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by tvasile on 12/2/2016.
 */
@Controller
@RequestMapping(value = "/")
public class MusicPlayerController  {

    @Autowired
    private UserService userService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private AutoCompleteService autoCompleteService;

    @Autowired
    private LyricsService lyricsService;

    @Autowired
    private CurrentSong currentSong;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("username", name);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(Model model) {
        model.addAttribute("userForm", new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String Register(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.save(userForm);

        return "redirect:/home";
    }

    @RequestMapping(value = "/audio", method = RequestMethod.GET)
    @ResponseBody
    public void playAudio(HttpServletRequest request, HttpServletResponse response, @RequestParam("songName") String songName, @RequestParam("artistName") String artistName) {
        currentSong.setSongTitle(songName);
        currentSong.setArtistName(artistName);
        musicService.play(songName, request, response);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public SongSearchDto searchAutocomplete(@RequestBody @Valid SongSearchRequestDto songSearchRequest) {
        return autoCompleteService.search(songSearchRequest);
    }

    @RequestMapping(value = "/lyrics", method = RequestMethod.POST)
    @ResponseBody
    public String getLyrics() {
        return lyricsService.getLyrics(currentSong);
    }

}
