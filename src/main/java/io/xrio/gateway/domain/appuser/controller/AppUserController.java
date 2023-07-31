package io.xrio.gateway.domain.appuser.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xrio.gateway.domain.appuser.exception.RefreshTokenMissingException;
import io.xrio.gateway.domain.appuser.model.AppUser;
import io.xrio.gateway.domain.appuser.exception.AppUserNotFoundException;
import io.xrio.gateway.domain.appuser.service.AppUserService;
import io.xrio.gateway.domain.role.exception.RoleNotFoundException;
import io.xrio.gateway.utils.JwtUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static io.xrio.gateway.utils.JwtUtils.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
@RestController
@RequestMapping("users")
@Data
@Slf4j
public class AppUserController {

    private final AppUserService appUserService;


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody AppUser appUser) {
        if (appUser == null)
            return ResponseEntity
                    .status(UNPROCESSABLE_ENTITY)
                    .body("User data is corrupted");
        return ResponseEntity
                .status(CREATED)
                .body(appUser);
    }

    @PostMapping("/link")
    public ResponseEntity<String> linkRole(@RequestBody UserRoleLinkingData linkingData) throws AppUserNotFoundException, RoleNotFoundException {
        if (linkingData == null || linkingData.getName() == null || linkingData.getUsername() == null)
                return ResponseEntity
                        .status(UNPROCESSABLE_ENTITY)
                        .body("User/role data is corrupted");
        appUserService.linkRoleToUser(linkingData.getUsername(), linkingData.getName());
        return ResponseEntity
                .ok()
                .body("Role: "+linkingData.getName()+" linked to user: "+linkingData.getUsername());
    }

    @GetMapping("/")
    public ResponseEntity<List<AppUser>> findAll() {
        return ResponseEntity
                .ok()
                .body(appUserService.findAll());
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, RefreshTokenMissingException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(JwtUtils.BEARER)){
            try {
                String token = authorizationHeader.substring(JwtUtils.BEARER.length());
                DecodedJWT decodedJWT = JwtUtils.getDecoded(token);
                String username = decodedJWT.getSubject();
                AppUser appUser = appUserService.findByUsername(username);
                Map<String, String> tokens = new HashMap<>();
                tokens.put(ACCESS_TOKEN_ID, JwtUtils.createAccessToken(appUser));
                tokens.put(REFRESH_TOKEN_ID, JwtUtils.createRefreshToken(appUser));
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e){
                log.error("Error refreshing token: {}", e.getMessage());
                response.setHeader("error","Error refreshing token: "+e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error", "Verification error");
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RefreshTokenMissingException();
        }
    }

}
