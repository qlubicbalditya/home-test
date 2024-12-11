package com.nutech.hometest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"banner_name", "banner_image", "description"})
public interface BannerData {
    
    @JsonProperty(value = "banner_name")
    String getBannerName();

    @JsonProperty(value = "banner_image")
    String getBannerImage();

    String getDescription();
}
