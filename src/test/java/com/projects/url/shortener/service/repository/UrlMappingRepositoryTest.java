package com.projects.url.shortener.service.repository;

import com.projects.url.shortener.service.model.UrlMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
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
}