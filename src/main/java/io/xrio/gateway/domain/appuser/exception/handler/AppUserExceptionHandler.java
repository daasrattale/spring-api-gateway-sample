package io.xrio.gateway.domain.appuser.exception.handler;

import io.xrio.gateway.domain.appuser.exception.AppUserDuplicatedException;
import io.xrio.gateway.domain.appuser.exception.AppUserNotFoundException;
import io.xrio.gateway.domain.appuser.exception.RefreshTokenMissingException;
import io.xrio.gateway.domain.appuser.exception.TokenExpiredException;
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
public class AppUserExceptionHandler {


    @ExceptionHandler(AppUserNotFoundException.class)
    private ResponseEntity<?> handleAppUserNotFoundException(AppUserNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("User ["+exception.getUsername()+"] nowhere to be found");
    }

    @ExceptionHandler(AppUserDuplicatedException.class)
    private ResponseEntity<?> handleAppUserDuplicatedException(AppUserDuplicatedException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("User ["+exception.getUsername()+"] already exists");
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
