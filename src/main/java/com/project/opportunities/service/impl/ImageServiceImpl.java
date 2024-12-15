package com.project.opportunities.service.impl;

import com.project.opportunities.model.Image;
import com.project.opportunities.repository.ImageRepository;
import com.project.opportunities.service.CloudinaryService;
import com.project.opportunities.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile file, Image.ImageType imageType) {
        Image image = new Image();
        image.setType(imageType);
        image.setUrlImage(cloudinaryService.uploadFile(file, imageType.getFolderName()));
        imageRepository.save(image);

        return image;
    }
}
