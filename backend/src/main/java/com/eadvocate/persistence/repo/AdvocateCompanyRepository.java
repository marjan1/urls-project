package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.AdvocateCompany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for AdvocateCompany model.
 */
public interface AdvocateCompanyRepository extends JpaRepository<AdvocateCompany, Long> {
    /**
     * Find AdvocateCompany by name
     * @param name String
     * @return AdvocateCompany
     */
    AdvocateCompany getByName(String name);

    /**
     * Find AdvocateCompany by email
     * @param email String
     * @return AdvocateCompany
     */
    AdvocateCompany getByEmail(String email);
}
