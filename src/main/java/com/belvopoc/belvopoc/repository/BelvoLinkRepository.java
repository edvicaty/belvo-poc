package com.belvopoc.belvopoc.repository;

import com.belvopoc.belvopoc.model.BelvoLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BelvoLinkRepository extends JpaRepository<BelvoLink, Long> {

    BelvoLink findByInstitutionIdAndUserId(Long institutionId, Long userId);

}
