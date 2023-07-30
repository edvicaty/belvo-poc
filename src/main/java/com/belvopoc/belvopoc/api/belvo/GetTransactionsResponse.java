package com.belvopoc.belvopoc.api.belvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTransactionsResponse {

    private String id;
    private String currency;
    private String category;
    private Long amount;
    private String status;
    private String date;
    private String description;
    private String type;

}
