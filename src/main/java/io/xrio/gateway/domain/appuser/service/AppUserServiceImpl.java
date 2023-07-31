package io.xrio.gateway.domain.appuser.service;

import io.xrio.gateway.domain.appuser.model.AppUser;
import io.xrio.gateway.domain.appuser.repository.AppUserRepository;
import io.xrio.gateway.domain.appuser.exception.AppUserDuplicatedException;
import io.xrio.gateway.domain.appuser.exception.AppUserNotFoundException;
import io.xrio.gateway.domain.role.exception.RoleDuplicatedException;
import io.xrio.gateway.domain.role.model.Role;
import io.xrio.gateway.domain.role.repository.RoleRepository;
import io.xrio.gateway.domain.role.exception.RoleNotFoundException;
import io.xrio.gateway.domain.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    final AppUserRepository appUserRepository;
    final RoleRepository roleRepository;
    final RoleService roleService;
    final PasswordEncoder passwordEncoder;

    @Override
    public AppUser save(AppUser appUser) throws AppUserDuplicatedException {
        if (appUserRepository.findByUsername(appUser.getUsername()) != null) {
            log.error("User with username: {} not found", appUser.getUsername());
            throw new AppUserDuplicatedException(appUser.getUsername());
        }
        log.info("Saving user with username: {}", appUser.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public void linkRoleToUser(String username, String roleName) throws AppUserNotFoundException, RoleNotFoundException {
        AppUser userFromDb = appUserRepository.findByUsername(username);
        if (userFromDb == null) {
            log.error("User with username: {} not found", username);
            throw new AppUserNotFoundException(username);
        }
        Role roleFromDb = roleRepository.findByName(roleName);
        if (roleFromDb == null) {
            log.error("Role with name: {} not found", roleName);
            throw new RoleNotFoundException(roleName);
        }
        userFromDb.getRoles().add(roleFromDb);
        log.info("Added role: {} to user: {}", roleName, username);
    }

    @Override
    public AppUser findByUsername(String username) {
        log.info("Retrieving user: {}", username);
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> findAll() {
        log.info("Retrieving all users");
        return appUserRepository.findAll();
    }

    @Override
    public void init() throws RoleDuplicatedException, AppUserDuplicatedException {
        appUserRepository.deleteAll();
        roleRepository.deleteAll();
        AppUser user = new AppUser();
        user.setName("elattar saad");
        user.setUsername("xrio");
        user.setPassword("daasrattale");
        Role role = new Role();
        role.setName("SUPER_ADMIN");
        roleService.save(role);
        user.setRoles(Collections.singletonList(role));
        save(user);

        user = new AppUser();
        user.setName("admin");
        user.setUsername("admin");
        user.setPassword("admin");
        role = new Role();
        role.setName("ADMIN");
        roleService.save(role);
        user.setRoles(Collections.singletonList(role));
        save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser userFromDb = appUserRepository.findByUsername(username);
        if (userFromDb == null){
            log.error("User : {} is nowhere to be found", username);
            throw new UsernameNotFoundException("User : "+username+" is nowhere to be found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userFromDb.getRoles().forEach(role ->
            authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new User(userFromDb.getUsername(), userFromDb.getPassword(), authorities);
    }
}

