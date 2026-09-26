package com.projects.url.shortener.service.service;

import java.net.URI;

public class UrlNormalization {

    public String normalizeUrl(String url) {

        StringBuilder normalizedUrl = new StringBuilder();

        URI uri = URI.create(url);
        String uriScheme = uri.getScheme();
        String uriHost = uri.getHost();
        String uriPath = uri.getPath();
        String uriQuery = uri.getQuery();
        String uriFragment = uri.getFragment();

        uriScheme = uriScheme.toLowerCase();
        uriHost = uriHost.toLowerCase();

        normalizedUrl
                .append(uriScheme)
                .append("://" )
                .append(uriHost)
                .append(uriPath);

        if (uriQuery!=null) {

            normalizedUrl
                    .append("?")
                    .append(uriQuery);
        }

        if(uriFragment!=null){
            normalizedUrl
                    .append("#")
                    .append(uriFragment);

        }

        return normalizedUrl.toString();

    }

}

