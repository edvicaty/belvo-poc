package com.belvopoc.belvopoc.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsRequest {

    @NotNull(message = "Institution must not be null")
    private String institution;

    private String dateFrom;
    private String dateTo;
}
