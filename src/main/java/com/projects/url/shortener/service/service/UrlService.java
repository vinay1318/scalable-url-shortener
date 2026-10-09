package com.projects.url.shortener.service.service;

import com.projects.url.shortener.service.exception.InvalidUrlException;
import com.projects.url.shortener.service.model.UrlMapping;
import com.projects.url.shortener.service.repository.UrlMappingRepository;
import com.projects.url.shortener.service.validation.UrlValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UrlService {

   private final UrlValidator urlValidator;

   private final UrlMappingRepository urlMappingRepository;

   public UrlService(UrlValidator urlValidator,UrlMappingRepository urlMappingRepository) {
           this.urlValidator = urlValidator;
           this.urlMappingRepository = urlMappingRepository;
   }
   public void createShortUrl(String longUrl){

       UrlMapping urlMapping = new UrlMapping();

       if(!urlValidator.isValidUrl(longUrl)){
           throw new InvalidUrlException("Please enter a valid url");
       }

       urlMapping.setLongUrl(longUrl);
       UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);


   }

   public String fetchLongUrl(String shortCode){


       Optional<UrlMapping> urlMapping = urlMappingRepository.findByShortCode(shortCode);

       if(urlMapping.isPresent()){
           return urlMapping.get().getLongUrl();
       }else{

           throw new RuntimeException("Short Code not found");
       }

   }


}
