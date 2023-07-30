package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.api.belvo.CreateLinkRequest;
import com.belvopoc.belvopoc.api.belvo.CreateLinkResponse;
import com.belvopoc.belvopoc.domain.Institution;
import com.belvopoc.belvopoc.repository.InstitutionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Override
    public Set<Institution> getAllInstitutions() {
        Set<Institution> institutionSet = new HashSet<>();
        institutionRepository.findAll().iterator().forEachRemaining(institutionSet::add);
        return institutionSet;
    }

    @Override
    public CreateLinkResponse createLink(CreateLinkRequest request, HttpServletRequest httpServletRequest) {
        return null;
    }
}
