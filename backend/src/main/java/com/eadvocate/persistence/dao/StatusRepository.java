package com.eadvocate.persistence.dao;

import com.eadvocate.persistence.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for Status model.
 */
public interface StatusRepository extends JpaRepository<Status, Short> {
    /**
     * Find status by Name
     * @param name String
     * @return Status
     */
    Status getByName(String name);
}
