package com.projects.url.shortener.service.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlNormalizationTest {

    @Test
    void testNormalizeUrls(){

        UrlNormalization urlNormalization = new UrlNormalization();

        String url1 = urlNormalization.normalizeUrl("HTTPS://amazon.com:443/proDucts/ABC?id=10#REVIEws");

        String url2 = urlNormalization.normalizeUrl("https://amazon.com:443/proDucts/ABC?id=10#REVIEws");


        assertEquals(url1,url2);




    }

}