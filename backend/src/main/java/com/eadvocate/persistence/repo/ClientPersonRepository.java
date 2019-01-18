package com.eadvocate.persistence.repo;


import com.eadvocate.persistence.model.AdvocateCompany;
import com.eadvocate.persistence.model.ClientPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Spring data repository for ClientCompany model.
 */
public interface ClientPersonRepository extends JpaRepository<ClientPerson,Long> {

    Page<ClientPerson> findAllByAdvocateCompany(AdvocateCompany advocateCompany, Pageable pageRequest);
}
