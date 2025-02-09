package com.project.opportunities.repository;

import com.project.opportunities.domain.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("FROM Document WHERE category = DocumentCategory.REPORT")
    Page<Document> findReportDocuments(Pageable pageable);

    @Query("FROM Document WHERE category = DocumentCategory.FOUNDING")
    Page<Document> findFoundingDocuments(Pageable pageable);
}
