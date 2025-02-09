package com.project.opportunities.mapper;

import com.project.opportunities.config.MapperConfig;
import com.project.opportunities.domain.dto.documents.request.UploadDocumentDto;
import com.project.opportunities.domain.dto.documents.response.DocumentDto;
import com.project.opportunities.domain.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

@Mapper(config = MapperConfig.class)
public interface DocumentMapper {
    DocumentDto toDto(Document document);

    @Mapping(target = "dropboxId", ignore = true)
    @Mapping(target = "fileSize", expression = "java(file.getSize())")
    @Mapping(target = "mimeType", expression = "java(file.getContentType())")
    Document toCreateDocument(UploadDocumentDto documentDto, MultipartFile file);
}
