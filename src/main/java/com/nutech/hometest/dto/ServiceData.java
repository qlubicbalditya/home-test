package com.nutech.hometest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"service_code", "service_name", "service_icon", "service_tariff"})
public interface ServiceData {
    
    @JsonIgnore
    String getId();

    @JsonProperty(value = "service_code")
    String getServiceCode();

    @JsonProperty(value = "service_name")
    String getServiceName();

    @JsonProperty(value = "service_icon")
    String getServiceIcon();

    @JsonProperty(value = "service_tariff")
    Integer getServiceTariff();
}
