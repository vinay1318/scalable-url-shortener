package com.projects.url.shortener.service.repository;

import com.projects.url.shortener.service.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
}
