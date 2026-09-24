package com.projects.url.shortener.service.service;

public class Base62Encoder {

    private static final String BASE62_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String base62Encoder(long id){

        StringBuilder result = new StringBuilder();


       if(id == 0){
           return String.valueOf(BASE62_CHARACTERS.charAt(0));
       }

        while(id > 0){

            long remainder = id % 62;

            id = id/62;


           result.append(BASE62_CHARACTERS.charAt((int) remainder));

        }

        return result.reverse().toString();

    }















}
