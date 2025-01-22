package com.project.opportunities.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String urlImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageType type;

    @Getter
    public enum ImageType {
        TEAM_MEMBER_AVATAR_IMAGE("member_avatars"),
        VOLUNTEER_AVATAR_IMAGE("volunteer_avatars"),
        PARTNER_AVATAR_IMAGE("partner_avatars"),
        NEWS_IMAGE("news"),
        PROJECT_IMAGE("projects"),
        GENERAL_INFO_IMAGE("general_info");

        private final String folderName;

        ImageType(String folderName) {
            this.folderName = folderName;
        }

    }
}
