package io.xrio.gateway.domain.appuser.repository;

import io.xrio.gateway.domain.appuser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
