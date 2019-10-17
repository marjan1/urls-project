package com.url.persistence.repo;

import com.url.persistence.model.Link;
import com.url.persistence.model.LinkTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring data repository for Judge model.
 */
public interface LinkTagRepository extends JpaRepository<LinkTag, Long> {

    Optional<List<LinkTag>> findAllByLink(Link link);
}
