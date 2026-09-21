package com.projects.url.shortener.service.validation;

import java.net.URI;

public class urlValidator {

    public boolean isValidUrl(String url){

        URI uri = URI.create(url);
        String uriScheme = uri.getScheme();
        String uriHost = uri.getHost();
        String uriPath = uri.getPath();

        return true;
    }

    private  boolean validUriScheme(String uriScheme){

        return "https".equals(uriScheme) || "http".equals(uriScheme);

    }











}
