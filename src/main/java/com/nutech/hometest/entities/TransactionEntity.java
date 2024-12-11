package com.nutech.hometest.entities;

import java.time.LocalDateTime;

import com.nutech.hometest.supports.TransactionTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "t_transaction")
@Getter @Setter
@ToString
public class TransactionEntity {
    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "invoice_number", length = 20)
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 20)
    private TransactionTypeEnum transactionType;

    @Column(name = "total_amount", columnDefinition = "numeric")
    private Integer totalAmount;

    @Column(name= "service_id", length = 36)
    private String serviceId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = true)
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false,
    foreignKey = @ForeignKey(
        name = "m_service_fk",
        foreignKeyDefinition = "FOREIGN KEY (service_id) REFERENCES m_service (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION"
    ))
    private ServiceEntity service;

    @Column(name = "created_on", columnDefinition = "timestamp without time zone")
    private LocalDateTime createdOn;

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
}
