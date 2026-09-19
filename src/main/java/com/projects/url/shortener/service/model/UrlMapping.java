package com.projects.url.shortener.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="urlmapping")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="short_code",nullable = false,unique = true)
    private String shortCode;

    @Column(name="long_url",nullable = false)
    private String longUrl;











}
