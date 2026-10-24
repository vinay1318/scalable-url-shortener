package com.projects.url.shortener.service.service;

import com.projects.url.shortener.service.model.UrlMapping;
import com.projects.url.shortener.service.repository.UrlMappingRepository;
import com.projects.url.shortener.service.validation.UrlValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UrlServiceTest {

    private UrlValidator urlValidator;
    private UrlMappingRepository urlMappingRepository;
    private UrlService urlService;

    private Base62Encoder base62Encoder;

    @BeforeEach
    void setUp() {

        urlValidator = Mockito.mock(UrlValidator.class);

        urlMappingRepository =
                Mockito.mock(UrlMappingRepository.class);

        base62Encoder = Mockito.mock(Base62Encoder.class);

        urlService =
                new UrlService(urlValidator, urlMappingRepository,base62Encoder);

    }

    @Test
    void shouldSaveValidUrl() {

        // Arrange
        String longUrl = "https://www.amazon.com";

        when(urlValidator.isValidUrl(longUrl))
                .thenReturn(true);

        when(urlMappingRepository.save(
                Mockito.any(UrlMapping.class)))
                .thenAnswer(invocation -> {
                    UrlMapping mapping = invocation.getArgument(0);
                    mapping.setId(10L);
                    return mapping;
                });

        when(base62Encoder.base62Encoder(10L))
                .thenReturn("a");

        // Act
        String result = urlService.createShortUrl(longUrl);

        // Assert
        ArgumentCaptor<UrlMapping> captor =
                ArgumentCaptor.forClass(UrlMapping.class);

        verify(urlMappingRepository, Mockito.times(2))
                .save(captor.capture());

        UrlMapping savedMapping = captor.getValue();

        assertEquals(longUrl, savedMapping.getLongUrl());
        assertEquals("a", savedMapping.getShortCode());
        assertEquals("a", result);
    }

    @Test
    void shouldReturnLongUrlWhenShortCodeExists() {

            UrlMapping urlMapping = new UrlMapping();
            urlMapping.setLongUrl("www.amazon.com");
            urlMapping.setShortCode("cb");

            when(urlMappingRepository.findByShortCode("cb")).thenReturn(Optional.of(urlMapping));

            String result = urlService.fetchLongUrl("cb");

            assertEquals("www.amazon.com", result);

        }

    @Test
    void shouldThrowExceptionWhenShortCodeDoesNotExist() {

        when(urlMappingRepository.findByShortCode("xyz"))
                .thenReturn(Optional.empty());

            assertThrows(
                    RuntimeException.class,
                    () -> urlService.fetchLongUrl("xyz")
            );
        }


    @Test
    void  shouldThrowExceptionWhenUrlIsExpired() {

        LocalDateTime now = LocalDateTime.now();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.amazon.com");
        urlMapping.setShortCode("abc");
        urlMapping.setExpiresAt(now.minusDays(1));

        when(urlMappingRepository.findByShortCode("abc")).thenReturn(Optional.of(urlMapping));

        RuntimeException exception = assertThrows(RuntimeException.class,() -> urlService.fetchLongUrl("abc"));

        assertEquals("Url is Expired",exception.getMessage());


    }

    @Test
    void shouldReturnLongUrlWhenNotExpired() {

        LocalDateTime now = LocalDateTime.now();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.amazon.com");
        urlMapping.setShortCode("ab");
        urlMapping.setExpiresAt(now.plusDays(3));


        when(urlMappingRepository.findByShortCode("ab")).thenReturn(Optional.of(urlMapping));

        String url1 = urlService.fetchLongUrl("ab");

        assertEquals(url1,"www.amazon.com");

    }

    @Test
    void shouldReturnLongUrlWhenExpirationIsNull() {

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.amazon.com");
        urlMapping.setShortCode("cb");

        when(urlMappingRepository.findByShortCode("cb")).thenReturn(Optional.of(urlMapping));

        String url1 = urlService.fetchLongUrl("cb");

        assertEquals("www.amazon.com",url1);


    }

}


