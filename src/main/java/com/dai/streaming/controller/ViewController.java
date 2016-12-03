package com.dai.streaming.controller;

import com.dai.streaming.utils.MultipartFileSender;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by tvasile on 12/2/2016.
 */
@Controller
@RequestMapping(value = "/")
public class ViewController  {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getArticle() {
        return "index";
    }

    @RequestMapping(value = "/audio", method = RequestMethod.GET)
    @ResponseBody
    public void playAudio(HttpServletRequest request, HttpServletResponse response) {
        String path = "E:\\Disclosure-Help_Me_Lose_My_Mind.mp3";
        File file = new File(path);
        try {
            MultipartFileSender.fromFile(file).with(request).with(response).serveResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
