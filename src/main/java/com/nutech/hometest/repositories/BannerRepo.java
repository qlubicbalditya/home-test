package com.nutech.hometest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nutech.hometest.dto.BannerData;
import com.nutech.hometest.entities.BannerEntity;

@Repository
public interface BannerRepo extends JpaRepository<BannerEntity, String>{
    
    @Query(nativeQuery = true,
        value = "SELECT id, banner_name AS bannerName, banner_image AS bannerImage, description"
        +" FROM m_banner")
    List<BannerData> findAllData();
}
