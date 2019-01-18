package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.Judge;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for Judge model.
 */
public interface JudgeRepository extends JpaRepository<Judge, Long> {
}
