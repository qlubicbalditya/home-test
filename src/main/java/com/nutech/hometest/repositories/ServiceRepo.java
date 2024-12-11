package com.nutech.hometest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nutech.hometest.dto.ServiceData;
import com.nutech.hometest.entities.ServiceEntity;

@Repository
public interface ServiceRepo extends JpaRepository<ServiceEntity, String>{
    
    @Query(nativeQuery = true,
        value = "SELECT id, service_code AS serviceCode, service_name AS serviceName, service_icon AS serviceIcon, service_tariff AS serviceTariff"
        +" FROM m_service")
    List<ServiceData> findAllData();

    @Query(nativeQuery = true,
        value = "SELECT id, service_code AS serviceCode, service_name AS serviceName, service_icon AS serviceIcon, service_tariff AS serviceTariff"
        +" FROM m_service"
        +" WHERE service_code = :serviceCode")
    ServiceData findDataByServiceCode(@Param("serviceCode") String serviceCode);
}
