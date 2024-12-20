package com.project.opportunities.repository;

import com.project.opportunities.model.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    @Query(value = "FROM Volunteer v WHERE v.status = 'ACTIVE'")
    Page<Volunteer> findAllByStatus_Active(Pageable pageable);

    @Query(value = "From Volunteer v WHERE v.status = 'PENDING'")
    Page<Volunteer> findAllByStatus_Pending(Pageable pageable);

    @Query(value = "FROM Volunteer v WHERE v.status = 'REJECTED'")
    Page<Volunteer> findAllByStatus_Rejected(Pageable pageable);
}
