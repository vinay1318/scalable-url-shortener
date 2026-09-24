package com.projects.url.shortener.service.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Base62EncoderTest {

    @Test
    void shouldEncodeIdToBase62() {

        Base62Encoder encoder = new Base62Encoder();

        String actual = encoder.base62Encoder(63);

        assertEquals("bb",actual);








    }
}