package com.eadvocate.persistence.dao;

import com.eadvocate.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

}
