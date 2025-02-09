package com.project.opportunities.service.integration.storage.interfaces;

import com.dropbox.core.v2.files.FileMetadata;
import java.io.InputStream;

public interface DropboxService {
    FileMetadata uploadFile(String filePath, InputStream fileStream);

    InputStream downloadFile(String filePath);

    String getDownloadUrl(String dropboxId);

    void deleteFile(String dropboxId);
}
