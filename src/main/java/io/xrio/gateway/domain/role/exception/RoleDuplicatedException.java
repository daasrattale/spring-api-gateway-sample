package io.xrio.gateway.domain.role.exception;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
public class RoleDuplicatedException extends RoleException {
    public RoleDuplicatedException(String username) {
        super(username);
    }
}
