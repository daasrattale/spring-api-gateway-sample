package io.xrio.gateway.domain.role.exception.handler;

import io.xrio.gateway.domain.appuser.exception.RefreshTokenMissingException;
import io.xrio.gateway.domain.appuser.exception.TokenExpiredException;
import io.xrio.gateway.domain.role.exception.RoleDuplicatedException;
import io.xrio.gateway.domain.role.exception.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 27/9/2021
 */
@ControllerAdvice
public class RoleExceptionHandler {


    @ExceptionHandler(RoleNotFoundException.class)
    private ResponseEntity<?> handle(RoleNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("User ["+exception.getName()+"] nowhere to be found");
    }

    @ExceptionHandler(RoleDuplicatedException.class)
    private ResponseEntity<?> handle(RoleDuplicatedException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("User ["+exception.getName()+"] already exists");
    }


    @ExceptionHandler(RefreshTokenMissingException.class)
    private ResponseEntity<?> handleRefreshTokenMissingException(RefreshTokenMissingException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("Refresh token is missing");
    }

    @ExceptionHandler(TokenExpiredException.class)
    private ResponseEntity<?> handleTokenExpiredException(TokenExpiredException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("The provided token is expired");
    }
}
