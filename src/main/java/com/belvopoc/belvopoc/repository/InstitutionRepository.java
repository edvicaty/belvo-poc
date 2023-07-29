package com.belvopoc.belvopoc.repository;

import com.belvopoc.belvopoc.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    Institution findByName(String name);

}
