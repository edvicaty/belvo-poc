package com.belvopoc.belvopoc.api.belvo;

import com.belvopoc.belvopoc.domain.Institution;
import com.belvopoc.belvopoc.service.BelvoLinkService;
import com.belvopoc.belvopoc.service.InstitutionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<CreateLinkResponse> createLink(
            @RequestBody CreateLinkRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(belvoLinkService.createLink(request, httpServletRequest));
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountsResponse[]> getBalances(
            @RequestBody AccountsRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(belvoLinkService.getAccounts(request, httpServletRequest));
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionsResponse[]> getTransactions(
            @RequestBody TransactionsRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(belvoLinkService.getTransactions(request, httpServletRequest));
    }
}
