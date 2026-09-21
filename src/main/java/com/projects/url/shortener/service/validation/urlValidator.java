package com.projects.url.shortener.service.validation;

import java.net.URI;

public class urlValidator {

    public boolean isValidUrl(String url){

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

        if(uriHost != null && !uriHost.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
