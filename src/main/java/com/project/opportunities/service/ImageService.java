package com.project.opportunities.service;

import com.project.opportunities.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uploadImage(MultipartFile file, Image.ImageType type);
}
