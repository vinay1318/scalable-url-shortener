package com.projects.url.shortener.service.repository;

import com.projects.url.shortener.service.model.UrlMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("dev")
class UrlMappingRepositoryTest {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Test
    void shouldSaveAndFetchUrlMapping() {

        // Arrange
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("www.google.com");

        // Act - Save
        UrlMapping savedUrlMapping =
                urlMappingRepository.save(urlMapping);

        Long generatedId = savedUrlMapping.getId();

        // Act - Fetch
        Optional<UrlMapping> fetchedUrlMapping =
                urlMappingRepository.findById(generatedId);

        // Assert
        assertNotNull(generatedId);
        assertTrue(fetchedUrlMapping.isPresent());
        assertEquals(
                "www.google.com",
                fetchedUrlMapping.get().getLongUrl()
        );
    }
}