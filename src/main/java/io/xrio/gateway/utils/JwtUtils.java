package io.xrio.gateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.xrio.gateway.config.JwtVarsConfig;
import io.xrio.gateway.domain.appuser.exception.TokenExpiredException;
import io.xrio.gateway.domain.appuser.model.AppUser;
import io.xrio.gateway.domain.role.model.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 27/9/2021
 */
public class JwtUtils {

    public static final String SECRET = JwtVarsConfig.SECRET;
    public static final String ISSUER = JwtVarsConfig.ISSUER;
    public static final long ACCESS_TOKEN_VALIDITY = JwtVarsConfig.ACCESS_TOKEN_VALIDITY;
    public static final long REFRESH_TOKEN_VALIDITY = JwtVarsConfig.REFRESH_TOKEN_VALIDITY;
    public static final String BEARER = JwtVarsConfig.BEARER; // do not remove the space!
    public static final String CLAIMS_KEY = JwtVarsConfig.CLAIMS_KEY;
    public static final String ACCESS_TOKEN_ID = JwtVarsConfig.ACCESS_TOKEN_ID;
    public static final String REFRESH_TOKEN_ID = JwtVarsConfig.REFRESH_TOKEN_ID;


    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET.getBytes());
    }

    public static JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }

    public static DecodedJWT getDecoded(String token) {
        return getVerifier().verify(token);
    }

    public static String createAccessToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withClaim(CLAIMS_KEY, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public static String createRefreshToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withClaim(CLAIMS_KEY, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public static String createAccessToken(AppUser userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withClaim(CLAIMS_KEY, userDetails.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public static String createRefreshToken(AppUser userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withClaim(CLAIMS_KEY, userDetails.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token) throws TokenExpiredException {
        DecodedJWT decodedJWT = getDecoded(token);
        if(decodedJWT.getExpiresAt().before(new Date())) {
            throw new TokenExpiredException();
        }
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim(CLAIMS_KEY).asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role->
            authorities.add(new SimpleGrantedAuthority(role))
        );
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
