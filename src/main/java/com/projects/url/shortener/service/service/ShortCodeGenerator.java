package com.projects.url.shortener.service.service;

import org.springframework.stereotype.Component;

@Component
public class ShortCodeGenerator {

    private final Base62Encoder base62EncoderConverter;

    public ShortCodeGenerator(Base62Encoder base62EncoderConverter) {
        this.base62EncoderConverter = base62EncoderConverter;
    }

    public String generateShortCode(long id){

        return base62EncoderConverter.base62Encoder(id);

    }

}
