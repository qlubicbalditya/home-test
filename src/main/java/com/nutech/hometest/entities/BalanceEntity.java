package com.nutech.hometest.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "t_balance")
@Getter @Setter
@ToString
public class BalanceEntity {
    
    @Id
    @Column(length = 36)
    private String id;

    @Column(columnDefinition = "numeric")
    private Integer amount;

    @Column(name= "profile_id", length = 36, nullable = false)
    private String profileId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = true)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false,
    foreignKey = @ForeignKey(
        name = "m_profile_fk",
        foreignKeyDefinition = "FOREIGN KEY (profile_id) REFERENCES m_profile (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE"
    ))
    private ProfileEntity profile;

    @Column(name = "top_up_date", columnDefinition = "timestamp without time zone")
    private LocalDateTime topUpDate;

}
