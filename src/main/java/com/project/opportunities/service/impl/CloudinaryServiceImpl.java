package com.project.opportunities.service.impl;

import com.cloudinary.Cloudinary;
import com.project.opportunities.exception.CloudinaryUploadPhotoException;
import com.project.opportunities.service.CloudinaryService;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Resource
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new CloudinaryUploadPhotoException("Invalid file type. Only images are allowed.");
        }
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("folder", folderName);

            Map<String, Object> uploadedFile
                    = castToMap(cloudinary.uploader().upload(file.getBytes(), options));

            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        } catch (IOException e) {
            throw new CloudinaryUploadPhotoException("Failed to upload file");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castToMap(Object rawMap) {
        return (Map<String, Object>) rawMap;
    }
}
