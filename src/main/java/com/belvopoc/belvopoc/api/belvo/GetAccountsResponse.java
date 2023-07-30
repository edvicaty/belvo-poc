package com.belvopoc.belvopoc.api.belvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountsResponse {

    private String id;
    private String institution;
    private String currency;
    private String category;
    private Long balanceCurrent;
    private Long balanceAvailable;

}