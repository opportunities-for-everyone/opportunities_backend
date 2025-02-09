package com.project.opportunities.service.core.impl;

import com.dropbox.core.v2.files.FileMetadata;
import com.project.opportunities.domain.dto.documents.request.UploadDocumentDto;
import com.project.opportunities.domain.dto.documents.response.DocumentDto;
import com.project.opportunities.domain.model.Document;
import com.project.opportunities.exception.DocumentProcessingException;
import com.project.opportunities.mapper.DocumentMapper;
import com.project.opportunities.repository.DocumentRepository;
import com.project.opportunities.service.core.interfaces.DocumentService;
import com.project.opportunities.service.integration.storage.interfaces.DropboxService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DropboxService dropboxService;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Override
    @Transactional
    public void uploadDocument(UploadDocumentDto uploadDocumentDto, MultipartFile file) {
        log.info("Starting document upload process for file: {}", file.getOriginalFilename());
        try {
            String dropboxPath = generateDropboxPath(
                    uploadDocumentDto.category(),
                    file.getOriginalFilename()
            );

            FileMetadata fileMetadata = dropboxService.uploadFile(
                    dropboxPath,
                    file.getInputStream()
            );
            log.debug("File uploaded to Dropbox with path: {}", dropboxPath);

            Document document = documentMapper.toCreateDocument(uploadDocumentDto, file);
            document.setDropboxId(fileMetadata.getId());;

            Document saved = documentRepository.save(document);
            log.info("Document successfully saved with ID: {}", saved.getId());

        } catch (IOException e) {
            log.error("Failed to process file upload: {}", e.getMessage(), e);
            throw new DocumentProcessingException("Failed to process file upload", e);
        }
    }

    @Override
    public Page<DocumentDto> getReportDocuments(Pageable pageable) {
        return documentRepository.findReportDocuments(pageable).map(documentMapper::toDto);
    }

    @Override
    public Page<DocumentDto> getFoundingDocuments(Pageable pageable) {
        return documentRepository.findFoundingDocuments(pageable).map(documentMapper::toDto);
    }

    @Override
    public String getDownloadableLink(Long id) {
        log.debug("Requesting downloadable link for document ID: {}", id);
        Document document = findDocumentById(id);
        return dropboxService.getDownloadUrl(document.getDropboxId());
    }

    @Override
    @Transactional
    public void deleteDocument(Long id) {
        log.info("Deleting document: {}", id);
        Document document = findDocumentById(id);
        dropboxService.deleteFile(document.getDropboxId());
        documentRepository.delete(document);
        log.info("Document successfully deleted with ID: {}", id);
    }

    private String generateDropboxPath(Document.DocumentCategory category, String fileName) {
        return String.format("/%s/%s", category.toString().toLowerCase(), fileName);
    }

    private Document findDocumentById(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> {
            log.error("Document not found by id: {}", id);
            return new DocumentProcessingException("Document not found by id: " + id);
        });
    }
}
