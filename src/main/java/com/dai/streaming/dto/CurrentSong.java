package com.dai.streaming.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS, value="session")
public class CurrentSong {

    @Getter @Setter
    String songTitle;

    @Getter @Setter
    private String artistName;

}
