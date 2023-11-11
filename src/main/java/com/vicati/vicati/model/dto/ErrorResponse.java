package com.vicati.vicati.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    String reason;
    int statusCode;
}
