package com.nutech.hometest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "m_profile", indexes = {
    @Index(name = "m_profile_idx1", columnList = "email")
}, uniqueConstraints = {
    @UniqueConstraint(name = "m_profile_un_email", columnNames = {"email"})
})
@Getter
@Setter
@ToString
public class ProfileEntity {
    
    @Id
    @Column(length = 36)
    private String id;

    @Column(length = 60)
    private String email;

    @Column(name = "first_name",length = 60)
    private String firstName;

    @Column(name = "last_name",length = 60)
    private String lastName;

    @Column(length = 255)
    private String password;

    @Column(name = "profile_image", length = 255)
    private String profileImage;


}
