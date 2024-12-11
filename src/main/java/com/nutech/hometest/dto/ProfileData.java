package com.nutech.hometest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"email", "first_name", "last_name", "profile_image"})
public interface ProfileData {
    
    @JsonIgnore
    String getId();

    String getEmail();

    @JsonProperty(value = "first_name")
    String getFirstName();

    @JsonProperty(value = "last_name")
    String getLastName();

    @JsonProperty(value = "profile_image")
    String getProfileImage();
}
