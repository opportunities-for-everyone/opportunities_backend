package com.project.opportunities.repository;

import com.project.opportunities.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    @Query(value = "FROM Partner p WHERE p.partnerStatus = 'ACTIVE'")
    Page<Partner> findAllActivePartners(Pageable pageable);

    @Query(value = "FROM Partner p WHERE p.partnerStatus = 'PENDING'")
    Page<Partner> findAllPendingPartners(Pageable pageable);
}
