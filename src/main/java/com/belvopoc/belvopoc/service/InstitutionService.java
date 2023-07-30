package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.api.belvo.CreateLinkRequest;
import com.belvopoc.belvopoc.api.belvo.CreateLinkResponse;
import com.belvopoc.belvopoc.domain.Institution;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public interface InstitutionService {
    Set<Institution> getAllInstitutions();

    CreateLinkResponse createLink(CreateLinkRequest request, HttpServletRequest httpServletRequest);
}
