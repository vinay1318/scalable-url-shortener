package com.projects.url.shortener.service.service;

import com.projects.url.shortener.service.exception.InvalidUrlException;
import com.projects.url.shortener.service.model.UrlMapping;
import com.projects.url.shortener.service.repository.UrlMappingRepository;
import com.projects.url.shortener.service.validation.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UrlService {

   private final UrlValidator urlValidator;

   private final UrlMappingRepository urlMappingRepository;

   private final Base62Encoder encode;

   public UrlService(UrlValidator urlValidator,UrlMappingRepository urlMappingRepository,Base62Encoder encode) {
           this.urlValidator = urlValidator;
           this.urlMappingRepository = urlMappingRepository;
           this.encode = encode;
   }

   @Transactional
   public String createShortUrl(String longUrl,String idempotencyKey){

       Optional<UrlMapping> existing_record = urlMappingRepository.findByIdempotencyKey(idempotencyKey);

       if(existing_record.isPresent()){

           return existing_record.get().getShortCode();
       }

       UrlMapping urlMapping = new UrlMapping();

       if(!urlValidator.isValidUrl(longUrl)){
           throw new InvalidUrlException("Please enter a valid url");
       }

       if(idempotencyKey!=null){
           urlMapping.setIdempotencyKey(idempotencyKey);
       }

       urlMapping.setLongUrl(longUrl);
       UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);

       Long generatedId = savedUrlMapping.getId();

       String generatedShortCode = encode.base62Encoder(generatedId);

       savedUrlMapping.setShortCode(generatedShortCode);

       urlMappingRepository.save(savedUrlMapping);

       return generatedShortCode;


   }

   public String fetchLongUrl(String shortCode){

       LocalDateTime now = LocalDateTime.now();


       Optional<UrlMapping> urlMapping = urlMappingRepository.findByShortCode(shortCode);


       if(urlMapping.isPresent()){
           if((urlMapping.get().getExpiresAt()!=null) && !urlMapping.get().getExpiresAt().isAfter(now)){
               throw new RuntimeException("Url is Expired");
           }

           return urlMapping.get().getLongUrl();
       }else{
           throw new RuntimeException("Short Code not found");
       }

   }


}
