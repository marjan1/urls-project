package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for Privilege model.
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {


}