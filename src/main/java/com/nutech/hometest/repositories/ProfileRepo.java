package com.nutech.hometest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nutech.hometest.dto.ProfileData;
import com.nutech.hometest.entities.ProfileEntity;

@Repository
public interface ProfileRepo extends JpaRepository<ProfileEntity, String>{
    
    @Modifying
    @Query(nativeQuery = true,
        value = "INSERT INTO m_profile(id, email, first_name, last_name, password)"
        +" VALUES(:id, :email, :firstName, :lastName, :password)")
    void saveData(@Param("id") String id, 
        @Param("email") String email, 
        @Param("firstName") String firstName, 
        @Param("lastName") String lastName, 
        @Param("password") String password);
    
    @Query(nativeQuery = true,
        value = "SELECT id, email, first_name AS firstName, last_name AS lastName, password, profile_image AS profileImage"
        +" FROM m_profile"
        +" WHERE id = :id")
    ProfileData findDataById(@Param("id") String id);

    @Query(nativeQuery = true,
        value = "SELECT id, email, first_name AS firstName, last_name AS lastName, password, profile_image AS profileImage"
        +" FROM m_profile"
        +" WHERE email = :email")
    ProfileData findDataByEmail(@Param("email") String email);

    @Query(nativeQuery = true,
        value = "SELECT id, email, first_name AS firstName, last_name AS lastName, password, profile_image AS profileImage"
        +" FROM m_profile"
        +" WHERE email = :email"
        +" AND password = :password")
    ProfileData findDataByEmailAndPasssword(@Param("email") String email, @Param("password") String password);

    @Modifying
    @Query(nativeQuery = true,
        value = "UPDATE m_profile"
        +" SET first_name = :firstName,"
        +" last_name = :lastName"
        +" WHERE email = :email")
    void updateData(@Param("email") String email, 
        @Param("firstName") String firstName, 
        @Param("lastName") String lastName);

    @Modifying
    @Query(nativeQuery = true,
        value = "UPDATE m_profile"
        +" SET profile_image = :fileName"
        +" WHERE email = :email")
    void updateProfile(@Param("email") String email, 
        @Param("fileName") String fileName);
}
