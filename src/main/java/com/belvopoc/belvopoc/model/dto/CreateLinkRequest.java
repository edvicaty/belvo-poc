package com.belvopoc.belvopoc.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLinkRequest {

    @NotNull(message = "institution must not be null")
    private String institution;

    @NotNull(message = "bankUsername must not be null")
    private String bankUsername;

    @NotNull(message = "bankPassword must not be null")
    private String bankPassword;

}
