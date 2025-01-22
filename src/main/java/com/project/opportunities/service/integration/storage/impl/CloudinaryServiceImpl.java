package com.project.opportunities.service.integration.storage.impl;

import com.cloudinary.Cloudinary;
import com.project.opportunities.exception.CloudinaryUploadPhotoException;
import com.project.opportunities.service.integration.storage.interfaces.CloudinaryService;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {
    @Resource
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        log.info("Starting file upload to folder: {}", folderName);
        log.debug("File details - name: {}, size: {} bytes, content type: {}",
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType());

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            log.error("Invalid file type attempted to upload: {}", contentType);
            throw new CloudinaryUploadPhotoException("Invalid file type. Only images are allowed.");
        }
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("folder", folderName);

            log.debug("Uploading file to Cloudinary");
            Map<String, Object> uploadedFile
                    = castToMap(cloudinary.uploader().upload(file.getBytes(), options));

            String publicId = (String) uploadedFile.get("public_id");
            String url = cloudinary.url().secure(true).generate(publicId);

            log.info("Successfully uploaded file. Public ID: {}", publicId);
            log.debug("File URL: {}", url);

            return url;

        } catch (IOException e) {
            log.error("Failed to upload file: {}", e.getMessage(), e);
            throw new CloudinaryUploadPhotoException("Failed to upload file");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castToMap(Object rawMap) {
        return (Map<String, Object>) rawMap;
    }
}
