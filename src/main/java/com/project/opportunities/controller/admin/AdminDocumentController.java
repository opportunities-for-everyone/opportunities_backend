package com.project.opportunities.controller.admin;

import com.project.opportunities.domain.dto.documents.request.UploadDocumentDto;
import com.project.opportunities.domain.model.Document;
import com.project.opportunities.service.core.interfaces.DocumentService;
import com.project.opportunities.validation.document.ValidDocument;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "Admin Documents Management",
        description = "Endpoints for managing documents by administrators"
)
@RestController
@RequestMapping("/api/admin/documents")
@RequiredArgsConstructor
public class AdminDocumentController {
    private final DocumentService documentService;

    @Operation(
            summary = "Upload new document",
            description = """
                    Upload a new document with metadata.
                    Only accessible by super administrators.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Document uploaded successfully",
                    content = @Content(schema = @Schema(implementation = Document.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data or file"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied - Requires SUPER_ADMIN role"
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadDocument(
            @Valid @RequestPart("document") UploadDocumentDto dto,
            @Valid
            @ValidDocument
            @RequestPart("file") MultipartFile file) {
        documentService.uploadDocument(dto, file);
    }

    @Operation(
            summary = "Delete document",
            description = """
                    Delete an existing document by its ID.
                    Only accessible by super administrators.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Document successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied - Requires SUPER_ADMIN role"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document not found"
            )
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
    }
}
