package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.exception.BelvoException;
import com.belvopoc.belvopoc.model.dto.AccountsResponse;
import com.belvopoc.belvopoc.model.dto.TransactionsResponse;
import com.belvopoc.belvopoc.model.dto.RegisterBelvoLinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    // Choosing Unix Epoch Date to list all possible data from Belvo API
    private final String defaultDateFrom = "1970-01-01";

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
            throw new BelvoException("Couldn't register Belvo Link from Belvo API");
        }

    }

    public AccountsResponse[] getAccountsByLink(String belvoLink) {
        String url = baseUrl + "/api/accounts/";
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("link", belvoLink);
        requestBodyMap.put("date_from", defaultDateFrom);
        requestBodyMap.put("date_to", getDateTo());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBodyMap, getPostHeaders());
        ResponseEntity<AccountsResponse[]> response = restTemplate.postForEntity(url, entity, AccountsResponse[].class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new BelvoException("Couldn't get accounts from Belvo API");
        }
    }

    public TransactionsResponse[] getTransactionsByLink(String belvoLink, String dateFrom, String dateTo) {
        String url = baseUrl + "/api/transactions/";
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("link", belvoLink);
        if (dateFrom != null && !dateFrom.isEmpty()) {
            requestBodyMap.put("date_from", dateFrom);
        } else {
            requestBodyMap.put("date_from", defaultDateFrom);
        }

        if (dateTo != null && !dateTo.isEmpty()) {
            requestBodyMap.put("date_to", dateTo);
        } else {
            requestBodyMap.put("date_to", getDateTo());
        }
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBodyMap, getPostHeaders());
        ResponseEntity<TransactionsResponse[]> response = restTemplate.postForEntity(url, entity, TransactionsResponse[].class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new BelvoException("Couldn't get transactions from Belvo API");
        }
    }

    private String getDateTo() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
