package io.xrio.gateway.domain.role.service;

import io.xrio.gateway.domain.role.exception.RoleDuplicatedException;
import io.xrio.gateway.domain.role.model.Role;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
public interface RoleService {
    Role save(Role role) throws RoleDuplicatedException;
}
