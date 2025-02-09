package com.project.opportunities.service.core.interfaces;

import com.project.opportunities.domain.dto.documents.request.UploadDocumentDto;
import com.project.opportunities.domain.dto.documents.response.DocumentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    void uploadDocument(UploadDocumentDto uploadDocumentDto, MultipartFile file);

    Page<DocumentDto> getReportDocuments(Pageable pageable);

    Page<DocumentDto> getFoundingDocuments(Pageable pageable);

    String getDownloadableLink(Long id);

    void deleteDocument(Long id);
}
