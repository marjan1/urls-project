package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.AdvocateCompany;
import com.eadvocate.persistence.model.ClientCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Spring data repository for ClientCompany model.
 */
public interface ClientCompanyRepository extends JpaRepository<ClientCompany,Long> {

    Page<ClientCompany> findAll(Pageable pageRequest);

    Page<ClientCompany> findAllByAdvocateCompany(AdvocateCompany advocateCompany, Pageable pageRequest);
}
