package io.xrio.gateway.domain.role.exception;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
public class RoleNotFoundException extends RoleException {

    public RoleNotFoundException(String username) {
        super(username);
    }
}
