package com.vicati.vicati.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email must not be empty")
    private String email;
    @NotEmpty(message = "Password must not be empty")
    private String password;

}
