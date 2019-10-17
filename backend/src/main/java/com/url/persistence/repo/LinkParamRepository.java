package com.url.persistence.repo;

import com.url.persistence.model.Link;
import com.url.persistence.model.LinkParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring data repository for Judge model.
 */
public interface LinkParamRepository extends JpaRepository<LinkParam, Long> {

    Optional<List<LinkParam>> findAllByLink(Link link);

}
