package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.OppositeSidePerson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for OppositeSidePerson model.
 */
public interface OppositeSidePersonRepository extends JpaRepository<OppositeSidePerson, Long> {
}
