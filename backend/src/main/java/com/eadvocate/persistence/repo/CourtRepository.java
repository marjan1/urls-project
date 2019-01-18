package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for Court model.
 */
public interface CourtRepository extends JpaRepository<Court,Long> {
}
