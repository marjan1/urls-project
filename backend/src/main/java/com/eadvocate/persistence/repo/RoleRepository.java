package com.eadvocate.persistence.repo;

import com.eadvocate.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring data repository for Role model.
 */
public interface RoleRepository  extends JpaRepository<Role, Long> {
    /**
     * Get Role by name from database.
     *
     * @param name of the Role.
     * @return Role object found by the name given as parameter.
     */
    Role getByName(String name);


}
