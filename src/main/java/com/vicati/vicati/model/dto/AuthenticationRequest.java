package com.vicati.vicati.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Email(message = "Email must be valid")
    @NotNull(message = "Email must not be null")
    private String email;
    @NotNull(message = "Password must not be null")
    private String password;

}
