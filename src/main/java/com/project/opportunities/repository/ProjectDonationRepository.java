package com.project.opportunities.repository;

import com.project.opportunities.domain.model.ProjectDonation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDonationRepository extends JpaRepository<ProjectDonation, Long> {
    Page<ProjectDonation> findAllByProject_Id(Long id, Pageable pageable);
}
