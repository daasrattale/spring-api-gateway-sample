package io.xrio.gateway.domain.appuser.exception;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
public class AppUserNotFoundException extends AppUserException{

    public AppUserNotFoundException(String username) {
        super(username);
    }
}
