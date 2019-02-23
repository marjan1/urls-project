package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.AdvocateCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring data repository for AdvocateCompany model.
 */
public interface AdvocateCompanyRepository extends JpaRepository<AdvocateCompany, Long> {
    /**
     * Find AdvocateCompany by name
     * @param name String
     * @return AdvocateCompany
     */
    Optional<AdvocateCompany> getByName(String name);

    /**
     * Find AdvocateCompany by email
     * @param email String
     * @return AdvocateCompany
     */
    Optional<AdvocateCompany> getByEmail(String email);


}
