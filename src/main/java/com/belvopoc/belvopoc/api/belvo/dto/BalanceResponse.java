package com.belvopoc.belvopoc.api.belvo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResponse {
    private Long current;
    private Long available;
}
