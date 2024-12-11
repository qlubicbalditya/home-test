package com.nutech.hometest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "m_service")
@Getter @Setter
@ToString
public class ServiceEntity {
    
    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "service_code",length = 60)
    private String serviceCode;

    @Column(name = "service_name",length = 255)
    private String serviceName;


    @Column(name = "service_icon",length = 255)
    private String serviceIcon;

    @Column(name = "service_tariff", columnDefinition = "numeric")
    private Integer serviceTariff;

}