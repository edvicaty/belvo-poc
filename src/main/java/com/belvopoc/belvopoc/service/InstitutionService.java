package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.model.Institution;

import java.util.Set;

public interface InstitutionService {
    Set<Institution> getAllInstitutions();

    Institution getInstitutionByName(String name);

}
