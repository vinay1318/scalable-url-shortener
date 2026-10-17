package com.projects.url.shortener.service.controller;

import com.projects.url.shortener.service.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenController {
    private final UrlService urlService;

    public ShortenController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> createShortUrl(@RequestBody String url){

        urlService.createShortUrl(url);

        return ResponseEntity.ok("URL Processed");

    }

}
