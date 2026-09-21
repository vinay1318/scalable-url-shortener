package com.projects.url.shortener.service.validation;

import lombok.Data;

import java.net.URI;

@Data
public class UrlValidator {


    public boolean isValidUrl(String url){

        if(url == null || url.isBlank()){
            return false;
        }

        try{
            URI uri = URI.create(url);
            String uriScheme = uri.getScheme();
            String uriHost = uri.getHost();
            //String uriPath = uri.getPath();

            return validUriScheme(uriScheme) && validUriHost(uriHost);
        } catch (IllegalArgumentException ex){
            return false;
        }
    }


    private  boolean validUriScheme(String uriScheme){

        return "https".equals(uriScheme) || "http".equals(uriScheme);

    }

    private boolean validUriHost(String uriHost){

        return uriHost != null && !uriHost.isEmpty();
    }




}
