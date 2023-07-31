package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.model.Institution;
import com.belvopoc.belvopoc.repository.InstitutionRepository;
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
    public Institution getInstitutionByName(String name) {
        return institutionRepository.findByName(name);
    }

}
