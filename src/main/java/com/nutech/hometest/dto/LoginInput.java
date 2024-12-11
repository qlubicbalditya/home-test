package com.nutech.hometest.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class LoginInput {
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.([a-zA-Z]{2,})$", message = "Format email tidak lengkap atau tidak valid")
    private String email;

    @Size(min = 8, message = "Password minimal harus 8 karakter")
    private String password;
}
