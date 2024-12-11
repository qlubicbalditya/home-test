package com.nutech.hometest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutech.hometest.dto.BannerData;
import com.nutech.hometest.dto.ServiceData;
import com.nutech.hometest.repositories.BannerRepo;
import com.nutech.hometest.repositories.ServiceRepo;

@Service
public class InformationService {
    
    @Autowired
    private BannerRepo bannerRepo;

    @Autowired
    private ServiceRepo serviceRepo;

    @Transactional(readOnly = true)
    public List<BannerData> listBanner(){
        return bannerRepo.findAllData();
    }

    @Transactional(readOnly = true)
    public List<ServiceData> listService(){
        return serviceRepo.findAllData();
    }
}
