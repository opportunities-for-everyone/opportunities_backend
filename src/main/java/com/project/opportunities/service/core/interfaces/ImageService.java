package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uploadImage(MultipartFile file, Image.ImageType type);
}
