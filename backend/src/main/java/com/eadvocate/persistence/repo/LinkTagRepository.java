package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.LinkTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for Judge model.
 */
public interface LinkTagRepository extends JpaRepository<LinkTag, Long> {
}
