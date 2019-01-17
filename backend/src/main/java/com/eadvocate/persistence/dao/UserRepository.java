package com.eadvocate.persistence.dao;

import com.eadvocate.persistence.model.AdvocateCompany;
import com.eadvocate.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring data repository for User model.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find User by email.
     *
     * @param email String
     * @return Optional of user
     */
    Optional<User> findByEmail(String email);

    /**
     * Delete user
     *
     * @param user to be deleted.
     */
    @Override
    void delete(User user);

    /**
     * Find all users that are in the page fit
     * @param pageRequest page information
     * @return page with users
     */
    Page<User>findAll(Pageable pageRequest);

    Page<User>findAllByAdvocateCompany(Pageable pageRequest, AdvocateCompany advocateCompany);

}
