package com.project.opportunities.service.integration.storage.impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.project.opportunities.exception.DropboxException;
import com.project.opportunities.service.integration.storage.DropboxActionResolver;
import com.project.opportunities.service.integration.storage.interfaces.DropboxService;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DropboxServiceImpl implements DropboxService {
    private final DbxClientV2 client;

    @Override
    public FileMetadata uploadFile(String filePath, InputStream fileStream) {
        return handleDropboxAction(()
                        -> client.files().uploadBuilder(filePath).uploadAndFinish(fileStream),
                String.format("Error uploading file: %s", filePath));
    }

    @Override
    public InputStream downloadFile(String filePath) {
        return handleDropboxAction(() -> client.files().download(filePath).getInputStream(),
                String.format("Error downloading file: %s", filePath));
    }

    @Override
    public String getDownloadUrl(String dropboxId) {
        try {
            Metadata metadata = client.files().getMetadata(dropboxId);
            return client.files().getTemporaryLink(metadata.getPathLower()).getLink();
        } catch (DbxException e) {
            throw new DropboxException("Cant get download url with dropbox fileID: "
                    + dropboxId);
        }
    }

    @Override
    public void deleteFile(String dropboxId) {
        try {
            client.files().deleteV2(dropboxId);
        } catch (DbxException e) {
            throw new DropboxException("Error deleting file: " + e.getMessage());
        }
    }

    private <T> T handleDropboxAction(DropboxActionResolver<T> action,
                                      String exceptionMessage) {
        try {
            return action.perform();
        } catch (Exception e) {
            String messageWithCause = String.format("%s with cause: %s",
                    exceptionMessage, e.getMessage());
            throw new DropboxException(messageWithCause, e);
        }
    }
}
