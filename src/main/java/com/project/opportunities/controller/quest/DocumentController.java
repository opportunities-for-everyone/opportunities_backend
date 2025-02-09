package com.project.opportunities.controller.quest;

import com.project.opportunities.domain.dto.documents.response.DocumentDto;
import com.project.opportunities.service.core.interfaces.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Documents",
        description = "Public endpoints for accessing organization documents"
)
@RestController
@RequestMapping(value = "/public/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @Operation(
            summary = "Get report documents",
            description = """
                    Retrieve a paginated list of all report documents
                    available for public viewing.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of report documents",
            content = @Content(schema = @Schema(implementation = DocumentDto.class))
    )
    @GetMapping(value = "/reports")
    public Page<DocumentDto> getReportDocuments(
            @ParameterObject @PageableDefault Pageable pageable) {
        return documentService.getReportDocuments(pageable);
    }

    @Operation(
            summary = "Get founding documents",
            description = """
                    Retrieve a paginated list of all founding documents
                    available for public viewing.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of founding documents",
            content = @Content(schema = @Schema(implementation = DocumentDto.class))
    )
    @GetMapping(value = "/foundings")
    public Page<DocumentDto> getFoundingDocuments(
            @ParameterObject @PageableDefault Pageable pageable) {
        return documentService.getFoundingDocuments(pageable);
    }

    @Operation(
            summary = "Get document download link",
            description = "Retrieve a download link for a specific document by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the document download link",
                    content = @Content(schema = @Schema(
                            type = "string",
                            description = "Download URL for the document"
                    ))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Document not found"
            )
    })
    @GetMapping(value = "/{id}")
    public String getDocumentById(@PathVariable Long id) {
        return documentService.getDownloadableLink(id);
    }
}
