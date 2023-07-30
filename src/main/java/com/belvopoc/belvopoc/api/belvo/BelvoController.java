package com.belvopoc.belvopoc.api.belvo;

import com.belvopoc.belvopoc.api.auth.dto.AuthenticationResponse;
import com.belvopoc.belvopoc.api.belvo.dto.*;
import com.belvopoc.belvopoc.domain.Institution;
import com.belvopoc.belvopoc.service.BelvoLinkService;
import com.belvopoc.belvopoc.service.InstitutionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/belvo")
@RequiredArgsConstructor
public class BelvoController {

    private final InstitutionService institutionService;
    private final BelvoLinkService belvoLinkService;

    @GetMapping("/institutions")
    public ResponseEntity<Set<Institution>> getAllInstitutions() {
        return ResponseEntity.ok(institutionService.getAllInstitutions());
    }

    @PostMapping("/link")
    public ResponseEntity<?> createLink(
            @RequestBody CreateLinkRequest request,
            HttpServletRequest httpServletRequest
    ) {
        try {
            CreateLinkResponse response = belvoLinkService.createLink(request, httpServletRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Link Created");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> getBalances(
            @RequestBody AccountsRequest request,
            HttpServletRequest httpServletRequest
    ) {
        try {
            AccountsResponse[] response = belvoLinkService.getAccounts(request, httpServletRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> getTransactions(
            @RequestBody TransactionsRequest request,
            HttpServletRequest httpServletRequest
    ) {
        try {
            TransactionsResponse[] response = belvoLinkService.getTransactions(request, httpServletRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
