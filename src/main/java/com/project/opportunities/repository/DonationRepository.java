package com.project.opportunities.repository;

import com.project.opportunities.domain.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
