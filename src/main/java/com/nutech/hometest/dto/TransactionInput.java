package com.nutech.hometest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @NoArgsConstructor
public class TransactionInput {
    
    @JsonProperty(value = "service_code")
    @NotNull(message = "Service atau Layanan tidak ditemukan")
    @NotBlank(message = "Service atau Layanan tidak ditemukan")
    private String serviceCode;
}
