package com.projects.url.shortener.service.service;

import com.projects.url.shortener.service.model.UrlMapping;
import com.projects.url.shortener.service.repository.UrlMappingRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("dev")
@Transactional
public class UrlServiceIntegrationTest {
    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Test
    void shouldFetchLongUrlFromDatabase(){

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.google.com");
        urlMapping.setShortCode("abc123");

        urlMappingRepository.save(urlMapping);

        String url1 = urlService.fetchLongUrl("abc123");
        assertEquals("www.google.com",url1);

    }

    @Test
    void shouldThrowExceptionWhenUrlExpired() {

        LocalDateTime now = LocalDateTime.now();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.google.com");
        urlMapping.setShortCode("abc123");
        urlMapping.setExpiresAt(now.minusDays(3));

        urlMappingRepository.save(urlMapping);

       RuntimeException exception =  assertThrows(RuntimeException.class,() -> urlService.fetchLongUrl("abc123"));

       assertEquals("Url is Expired", exception.getMessage());

    }


}
