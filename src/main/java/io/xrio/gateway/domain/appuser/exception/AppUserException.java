package io.xrio.gateway.domain.appuser.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AppUserException extends Exception{
    protected final String username;
}
