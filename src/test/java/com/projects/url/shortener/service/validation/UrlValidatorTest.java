package com.projects.url.shortener.service.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlValidatorTest {

    private UrlValidator urlValidator;

    @BeforeEach
    void setUp() {
        urlValidator = new UrlValidator();
    }

    @Test
    void shouldRejectNull(){

        boolean result = urlValidator.isValidUrl(null);

        assertFalse(result);

    }

    @Test
    void shouldRejectBlank(){

        boolean result = urlValidator.isValidUrl("");

        assertFalse(result);
    }

    @Test
    void shouldRejectUrlWithoutScheme(){

        boolean result = urlValidator.isValidUrl("amazon.com");

        assertFalse(result);

    }
    @Test
    void shouldRejectUnsupportedScheme(){


        boolean result = urlValidator.isValidUrl("ftp://amazon.com");

        assertFalse(result);

    }

    @Test
    void shouldAcceptUppercaseHttpScheme(){


        boolean result = urlValidator.isValidUrl("HTTPS://AMAZON.COM");

        assertTrue(result);

    }

    @Test
    void shouldRejectMissingHostWithHttps(){

        boolean result = urlValidator.isValidUrl("https://");

        assertFalse(result);

    }

    @Test
    void shouldRejectMissingHostWithHttp(){

        boolean result = urlValidator.isValidUrl("http://");

        assertFalse(result);

    }

    @Test
    void shouldRejectUrlWithPathButMissingHost(){

        boolean result = urlValidator.isValidUrl("https:///products");

        assertFalse(result);

    }

    @Test
    void shouldAcceptValidHttpsUrl(){

        boolean result = urlValidator.isValidUrl("https://amazon.com");

        assertTrue(result);

    }

    @Test
    void shouldAcceptValidUrlWithPort(){

        boolean result = urlValidator.isValidUrl("http://localhost:8080/test");

        assertTrue(result);
    }

    @Test
    void shouldRejectWhitespaceOnlyUrl() {

        boolean result = urlValidator.isValidUrl("     ");

        assertFalse(result);
    }

}