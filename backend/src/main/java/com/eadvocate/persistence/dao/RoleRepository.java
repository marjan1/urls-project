package com.eadvocate.persistence.dao;

import com.eadvocate.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    Role getByName(String name);


}
