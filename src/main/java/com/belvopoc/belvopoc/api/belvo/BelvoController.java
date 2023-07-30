package com.belvopoc.belvopoc.api.belvo;

import com.belvopoc.belvopoc.domain.Institution;
import com.belvopoc.belvopoc.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/belvo")
@RequiredArgsConstructor
public class BelvoController {

    private final InstitutionService institutionService;

    @GetMapping("/institutions")
    public ResponseEntity<Set<Institution>> getAllInstitutions() {
        return ResponseEntity.ok(institutionService.getAllInstitutions());
    }
}
