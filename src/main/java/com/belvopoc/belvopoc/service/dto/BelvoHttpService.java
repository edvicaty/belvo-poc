package com.belvopoc.belvopoc.service.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


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

    public RegisterBelvoLinkResponse registerBelvoLink(String institution, String bankUsername, String bankPassword) {
        String url = baseUrl + "/api/links/";
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("institution", institution);
        requestBodyMap.put("username", bankUsername);
        requestBodyMap.put("password", bankPassword);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBodyMap, getPostHeaders());
        ResponseEntity<RegisterBelvoLinkResponse> response = restTemplate.postForEntity(url, entity, RegisterBelvoLinkResponse.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }

    }

}
