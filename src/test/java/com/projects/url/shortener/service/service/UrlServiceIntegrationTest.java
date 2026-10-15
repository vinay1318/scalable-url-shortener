package com.projects.url.shortener.service.service;

import com.projects.url.shortener.service.model.UrlMapping;
import com.projects.url.shortener.service.repository.UrlMappingRepository;
import jakarta.persistence.EntityManager;
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

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldFetchLongUrlFromDatabase(){

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.google.com");
        urlMapping.setShortCode("abc123");

        urlMappingRepository.save(urlMapping);

        entityManager.flush();
        entityManager.clear();

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

        entityManager.flush();
        entityManager.clear();

       RuntimeException exception =  assertThrows(RuntimeException.class,() -> urlService.fetchLongUrl("abc123"));

       assertEquals("Url is Expired", exception.getMessage());

    }

    @Test
    void shouldFetchLongUrlWhenExpirationIsNull() {

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.amazon.com");
        urlMapping.setShortCode("dummy42");

        urlMappingRepository.save(urlMapping);

        entityManager.flush();
        entityManager.clear();

        String url1 = urlService.fetchLongUrl("dummy42");

        assertEquals("www.amazon.com",url1);

    }

    @Test
    void shouldThrowExceptionWhenShortCodeDoesNotExist() {

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> urlService.fetchLongUrl("nonexistent42")
        );

        assertEquals("Short Code not found", exception.getMessage());
    }




}

