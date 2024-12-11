package com.nutech.hometest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nutech.hometest.dto.LoginData;
import com.nutech.hometest.dto.LoginInput;
import com.nutech.hometest.dto.ProfileData;
import com.nutech.hometest.dto.ProfileInput;
import com.nutech.hometest.dto.ProfileUpdateInput;
import com.nutech.hometest.services.MembershipService;
import com.nutech.hometest.supports.BasicResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Module Membership")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MembershipApi {
    
    @Autowired
    private MembershipService service;

    @Operation(summary = "Registration")
    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<ProfileData>> registration(
        @RequestBody(required = true) @Valid ProfileInput input
    )throws Exception{
        ProfileData data = service.createProfile(input);
        return ResponseEntity.ok(new BasicResponse<ProfileData>(data, "Registrasi berhasil silahkan login"));
    }

    @Operation(summary = "Login")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<LoginData>> login(
        @RequestBody(required = true) @Valid LoginInput input
    )throws Exception{
        
        LoginData data = service.login(input);
        if (data == null) {
            return new ResponseEntity<>(new BasicResponse<>(103, "Username atau password salah"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(new BasicResponse<LoginData>(data, "Login Sukses"));
    }

    @Operation(summary = "Profile")
    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<ProfileData>> getProfile(
        @AuthenticationPrincipal String email
    )throws Exception{
        
        if (email == null) {
            return new ResponseEntity<>(new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa"), HttpStatus.UNAUTHORIZED);
        }
        ProfileData data = service.getProfile(email);
        return ResponseEntity.ok(new BasicResponse<ProfileData>(data, "Sukses"));
    }

    @Operation(summary = "Profile Update")
    @PutMapping(value = "/profile/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<ProfileData>> updateProfile(
        @AuthenticationPrincipal String email,
        @RequestBody(required = true) @Valid ProfileUpdateInput input
    )throws Exception{
        
        if (email == null) {
            return new ResponseEntity<>(new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa"), HttpStatus.UNAUTHORIZED);
        }
        ProfileData data = service.updateProfile(email, input);
        return ResponseEntity.ok(new BasicResponse<ProfileData>(data, "Sukses"));
    }

    @Operation(summary = "Profile Image")
    @PutMapping(value = "/profile/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse<ProfileData>> uploadImage(
        @AuthenticationPrincipal String email,
        @RequestParam(value = "file") MultipartFile file
    )throws Exception{
    
        if (email == null) {
            return new ResponseEntity<>(new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa"), HttpStatus.UNAUTHORIZED);
        }

        if (!service.isValidImageFile(file)) {
            return new ResponseEntity<>(new BasicResponse<>(102, "Format Image tidak sesuai"), HttpStatus.BAD_REQUEST);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ProfileData data = service.updateImage(email, fileName);

        return ResponseEntity.ok(new BasicResponse<ProfileData>(data, "Sukses"));
    }
}
