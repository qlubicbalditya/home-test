package com.nutech.hometest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "m_banner")
@Getter @Setter
@ToString
public class BannerEntity {
    
    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "banner_name",length = 60)
    private String bannerName;

    @Column(name = "banner_image",length = 255)
    private String bannerImage;

    @Column(columnDefinition = "TEXT")
    private String description;

}
