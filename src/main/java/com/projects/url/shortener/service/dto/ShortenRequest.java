package com.projects.url.shortener.service.dto;


import jakarta.validation.constraints.NotBlank;

public record ShortenRequest(@NotBlank(message = "Original Url Cannot be Blank") String orignalUrl){




}





