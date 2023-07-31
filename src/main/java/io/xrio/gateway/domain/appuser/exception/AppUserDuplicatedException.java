package io.xrio.gateway.domain.appuser.exception;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
public class AppUserDuplicatedException extends AppUserException {
    public AppUserDuplicatedException(String username) {
        super(username);
    }
}
