package com.nutech.hometest.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nutech.hometest.config.JwtService;
import com.nutech.hometest.dto.LoginData;
import com.nutech.hometest.dto.LoginInput;
import com.nutech.hometest.dto.ProfileData;
import com.nutech.hometest.dto.ProfileInput;
import com.nutech.hometest.dto.ProfileUpdateInput;
import com.nutech.hometest.repositories.ProfileRepo;
import com.nutech.hometest.supports.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MembershipService {
    
    @Autowired
    private ProfileRepo repo;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public ProfileData createProfile(ProfileInput input){
        String id = Utils.createUUID();
        repo.saveData(id, input.getEmail(), input.getFirstName(), input.getLastName(), input.getPassword());
        return repo.findDataById(id);
    }

    @Transactional(readOnly = true)
    public LoginData login(LoginInput input){
        ProfileData data = repo.findDataByEmailAndPasssword(input.getEmail(), input.getPassword());
        if(data == null){
            return null;
        }
        return new LoginData(jwtService.generateToken(data.getEmail()));
    }

    @Transactional(readOnly = true)
    public ProfileData getProfile(String email){
        ProfileData data = repo.findDataByEmail(email);
        if(data == null){
            return null;
        }
        return data;
    }

    @Transactional
    public ProfileData updateProfile(String email, ProfileUpdateInput input){
        repo.updateData(email, input.getFirstName(), input.getLastName());
        return repo.findDataByEmail(email);
    }

    @Transactional
    public ProfileData updateImage(String email, String fileName){
        repo.updateProfile(email, fileName);
        return repo.findDataByEmail(email);
    }

    public boolean isValidImageFile(MultipartFile file) {
        List<String> extention = Arrays.asList("jpg", "jpeg", "png");
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(fileName).toLowerCase();

        if (!extention.contains(fileExtension)) {
            return false;
        }

        String mimeType = file.getContentType();
        return mimeType != null && (mimeType.equals("image/jpeg") || mimeType.equals("image/png"));
    }

    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return fileName.substring(index + 1);
    }
}
