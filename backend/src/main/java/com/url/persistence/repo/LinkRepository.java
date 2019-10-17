package com.url.persistence.repo;

import com.url.persistence.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for Judge model.
 */
public interface LinkRepository extends JpaRepository<Link, Long> {
}
