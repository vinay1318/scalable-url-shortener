package com.projects.url.shortener.service.controller;

import com.projects.url.shortener.service.dto.ShortenRequest;
import com.projects.url.shortener.service.dto.ShortenUrlResponse;
import com.projects.url.shortener.service.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenController {
    private final UrlService urlService;

    public ShortenController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> createShortUrl(@Valid @RequestBody ShortenRequest request, @RequestHeader(value = "idempotency-key",required = false) String idempotencyKey){

        String url = request.originalUrl();

        String shortCode = urlService.createShortUrl(url,idempotencyKey);

        ShortenUrlResponse response = new ShortenUrlResponse();

        response.setShortCode(shortCode);

        response.setShortUrl("http://localhost:8080/" + shortCode);

        return ResponseEntity.ok(response);

    }

}
