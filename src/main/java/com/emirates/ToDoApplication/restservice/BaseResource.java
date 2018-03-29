package com.emirates.ToDoApplication.restservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class BaseResource {

    public void setLocationHeader(HttpHeaders httpHeaders, String uri) {
        try {
            httpHeaders.setLocation(new URI(uri));
        } catch (URISyntaxException e) {
            log.error("error while setting location header", e);
        }
    }
}
