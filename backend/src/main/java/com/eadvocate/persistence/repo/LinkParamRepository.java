package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.LinkParam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for Judge model.
 */
public interface LinkParamRepository extends JpaRepository<LinkParam, Long> {
}
