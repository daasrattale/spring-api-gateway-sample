package io.xrio.gateway.domain.appuser.service;


import io.xrio.gateway.domain.appuser.exception.AppUserDuplicatedException;
import io.xrio.gateway.domain.appuser.exception.AppUserNotFoundException;
import io.xrio.gateway.domain.appuser.model.AppUser;
import io.xrio.gateway.domain.role.exception.RoleDuplicatedException;
import io.xrio.gateway.domain.role.exception.RoleNotFoundException;

import java.util.List;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
public interface AppUserService {
    AppUser save(AppUser appUser) throws AppUserDuplicatedException;
    void linkRoleToUser(String username, String roleName) throws AppUserNotFoundException, RoleNotFoundException;
    AppUser findByUsername(String username);
    List<AppUser> findAll();

    void init() throws RoleDuplicatedException, AppUserDuplicatedException;
}
