package io.xrio.gateway.domain.role.repository;

import io.xrio.gateway.domain.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
