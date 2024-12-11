package com.nutech.hometest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutech.hometest.dto.BannerData;
import com.nutech.hometest.dto.ServiceData;
import com.nutech.hometest.services.InformationService;
import com.nutech.hometest.supports.BasicResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Module Information")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InformationApi {
    
    @Autowired
    private InformationService service;

    @Operation(summary = "Banner")
    @GetMapping(value = "/banner", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<BannerData>> listBanner()throws Exception{
        return ResponseEntity.ok(new BasicResponse<BannerData>(service.listBanner(), "Sukses"));
    }

    @Operation(summary = "Service")
    @GetMapping(value = "/service", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<ServiceData>> listService(
        @AuthenticationPrincipal String email
    )throws Exception{
        
        if (email == null) {
            return new ResponseEntity<>(new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa"), HttpStatus.UNAUTHORIZED);
        }
        
        return ResponseEntity.ok(new BasicResponse<ServiceData>(service.listService(), "Sukses"));
    }
}
