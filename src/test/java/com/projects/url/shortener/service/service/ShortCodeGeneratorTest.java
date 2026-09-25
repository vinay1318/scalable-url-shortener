package com.projects.url.shortener.service.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortCodeGeneratorTest {

    @Test
    void shouldGenerateDifferentShortCodesForDifferentIds() {

        Base62Encoder encoder = new Base62Encoder();
        ShortCodeGenerator generator = new ShortCodeGenerator(encoder);


        String actual1 = generator.generateShortCode(63);
        String actual2 = generator.generateShortCode(75);

        assertNotEquals(actual1,actual2);
    }
}