package com.url.persistence.repo;

import com.url.persistence.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring data repository for Judge model.
 */
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<List<Link>> findAllByUrl(String url);
}
