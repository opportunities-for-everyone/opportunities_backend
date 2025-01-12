package com.project.opportunities.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "partners")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String partnerName;

    @Column(nullable = false)
    private String cooperationGoal;

    @Column(nullable = false)
    private String siteUrl;

    @Column(nullable = false)
    private String legalAddress;

    @Column(nullable = false)
    private String identificationCode;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "partner", optional = false)
    private Director director;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_id", nullable = false)
    private Image logo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartnerType partnerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartnerStatus partnerStatus;

    @Getter
    public enum PartnerType {
        FOP("ФОП(Фізична Особа-Підприємець)"),
        LEGAL_PERSON("Юридична особа"),
        PP("ПП(Приватне Підприємство)"),
        COOPERATIVE("Кооператив"),
        BUSINESS_ASSOCIATION("Господарська Організація");

        private final String description;

        PartnerType(String description) {
            this.description = description;
        }

    }

    public enum PartnerStatus {
        ACTIVE,
        PENDING,
        CANCELED
    }
}
