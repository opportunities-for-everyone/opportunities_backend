package com.project.opportunities.repository;

import com.project.opportunities.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
