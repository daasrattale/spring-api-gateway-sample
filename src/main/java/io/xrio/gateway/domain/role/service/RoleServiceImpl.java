package io.xrio.gateway.domain.role.service;

import io.xrio.gateway.domain.role.exception.RoleDuplicatedException;
import io.xrio.gateway.domain.role.model.Role;
import io.xrio.gateway.domain.role.repository.RoleRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
@Data
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService{

    final RoleRepository roleRepository;

    @Override
    public Role save(Role role) throws RoleDuplicatedException {
        if (roleRepository.findByName(role.getName()) != null) {
            log.error("Role with name: {} not found", role.getName());
            throw new RoleDuplicatedException(role.getName());
        }
        log.info("Saving user with username: {}", role.getName());
        return roleRepository.save(role);
    }
}
