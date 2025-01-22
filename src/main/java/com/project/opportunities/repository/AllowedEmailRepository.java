package com.project.opportunities.repository;

import com.project.opportunities.domain.model.AllowedEmail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowedEmailRepository extends JpaRepository<AllowedEmail, Long> {
    boolean existsByEmail(String email);

    Optional<AllowedEmail> findByEmail(String email);
}
