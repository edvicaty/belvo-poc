package com.belvopoc.belvopoc.service.dto;

import com.belvopoc.belvopoc.domain.BelvoLink;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class BelvoHttpService {
    private final RestTemplate restTemplate;

    @Value("${api.belvoApiBaseUrl}")
    private String baseUrl;

    @Value("${api.belvoApiId}")
    private String apiId;

    @Value("${api.belvoApiPassword}")
    private String apiPassword;

    private HttpHeaders getPostHeaders() {
        String authorizationHeader = apiId + ":" + apiPassword;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(authorizationHeader.getBytes()));
        return headers;
    }

}
