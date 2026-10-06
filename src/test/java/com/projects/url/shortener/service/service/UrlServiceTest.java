package com.projects.url.shortener.service.service;

import com.projects.url.shortener.service.model.UrlMapping;
import com.projects.url.shortener.service.repository.UrlMappingRepository;
import com.projects.url.shortener.service.validation.UrlValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UrlServiceTest {

    private UrlValidator urlValidator;
    private UrlMappingRepository urlMappingRepository;
    private UrlService urlService;

    @BeforeEach
    void setUp() {

        urlValidator = Mockito.mock(UrlValidator.class);

        urlMappingRepository =
                Mockito.mock(UrlMappingRepository.class);

        urlService =
                new UrlService(urlValidator, urlMappingRepository);

    }

//    @Test
//    void shouldSaveValidUrl() {
//
//        // Arrange
//        String longUrl = "https://www.amazon.com";
//
//        when(urlValidator.isValidUrl(longUrl))
//                .thenReturn(true);
//
//        // Act
//        urlService.createShortUrl(longUrl);
//
//        // Assert
//        ArgumentCaptor<UrlMapping> captor =
//                ArgumentCaptor.forClass(UrlMapping.class);
//
//        verify(urlMappingRepository).save(captor.capture());
//
//        UrlMapping savedMapping = captor.getValue();
//
//        assertEquals(longUrl, savedMapping.getLongUrl());
    }

