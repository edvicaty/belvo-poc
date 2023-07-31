package com.belvopoc.belvopoc.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsRequest {
    @NotNull(message = "institution must not be null")
    private String institution;
}
