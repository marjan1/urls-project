package com.url.persistence.repo;

import com.url.persistence.model.User;
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
    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndIdNot(String email, Long id);



}
