package com.belvopoc.belvopoc.api.belvo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsResponse {

    private String id;
    private String currency;
    private String category;
    private Long amount;
    private String status;
    private String created_at;
    private String description;
    private String type;
    private AccountResponse account;

}