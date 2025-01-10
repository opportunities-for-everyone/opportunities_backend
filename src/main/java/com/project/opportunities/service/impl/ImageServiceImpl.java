package com.project.opportunities.service.impl;

import com.project.opportunities.model.Image;
import com.project.opportunities.repository.ImageRepository;
import com.project.opportunities.service.CloudinaryService;
import com.project.opportunities.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile file, Image.ImageType imageType) {
        log.info("Uploading image. Type: {}, File name: {}", imageType, file.getOriginalFilename());

        String uploadedUrl = cloudinaryService.uploadFile(file, imageType.getFolderName());
        log.info("Image successfully uploaded to Cloudinary. URL: {}", uploadedUrl);

        Image image = new Image();
        image.setType(imageType);
        image.setUrlImage(uploadedUrl);

        imageRepository.save(image);
        log.info("Image successfully saved in database. Image ID: {}, URL: {}",
                image.getId(), uploadedUrl);
        return image;
    }
}
