package com.belvopoc.belvopoc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsResponse {

    private String id;
    private InstitutionResponse institution;
    private String currency;
    private String category;
    private BalanceResponse balance;

}