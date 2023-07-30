package com.belvopoc.belvopoc.api.belvo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLinkRequest {

    private String institution;
    private String bankUsername;
    private String bankPassword;

}