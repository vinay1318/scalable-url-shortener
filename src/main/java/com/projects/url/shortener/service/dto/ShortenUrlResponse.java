package com.projects.url.shortener.service.dto;

import lombok.Data;

@Data
public class ShortenUrlResponse {

    private String shortCode;
    private String shortUrl;

}
