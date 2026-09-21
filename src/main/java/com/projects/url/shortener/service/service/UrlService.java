package com.projects.url.shortener.service.service;

import com.projects.url.shortener.service.exception.InvalidUrlException;
import com.projects.url.shortener.service.validation.UrlValidator;
import org.springframework.stereotype.Service;


@Service
public class UrlService {

   private final UrlValidator urlValidator;
   public UrlService(UrlValidator urlValidator) {
           this.urlValidator = urlValidator;
   }
   public void createShortUrl(String longUrl){

       if(!urlValidator.isValidUrl(longUrl)){
           throw new InvalidUrlException("Please enter a valid url");
       }

   }

















}
