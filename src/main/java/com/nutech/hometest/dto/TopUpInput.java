package com.nutech.hometest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class TopUpInput {

    @JsonProperty(value = "top_up_amount")
    @Min(value = 0, message = "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
    private Integer topUpAmount;
}
