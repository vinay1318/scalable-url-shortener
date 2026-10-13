package com.projects.url.shortener.service.repository;

import com.projects.url.shortener.service.model.UrlMapping;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@Transactional
class UrlMappingRepositoryTest {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Test
    void shouldSaveAndFetchUrlMapping(){

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.amazon.com");

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);

        Long generatedId = savedUrlMapping.getId();

        Optional<UrlMapping> fetchedUrlMapping = urlMappingRepository.findById(generatedId);

        assertNotNull(generatedId);
        assertFalse(fetchedUrlMapping.isEmpty());
        assertEquals("www.amazon.com",fetchedUrlMapping.get().getLongUrl());

    }

    @Test
    void shouldFindUrlMappingByShortCode() {

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.google.com");
        urlMapping.setShortCode("test39abc");

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);

        Optional <UrlMapping> result = urlMappingRepository.findByShortCode("test39abc");

        assertTrue(result.isPresent());
        assertEquals("www.google.com",result.get().getLongUrl());


    }

    @Test
    void shouldReturnEmptyWhenShortCodeDoesNotExist() {

        Optional<UrlMapping> result =
                urlMappingRepository.findByShortCode("nonexistent42");

        assertTrue(result.isEmpty());



    }

}