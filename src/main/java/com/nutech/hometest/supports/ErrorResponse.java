package com.nutech.hometest.supports;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> details;
}

