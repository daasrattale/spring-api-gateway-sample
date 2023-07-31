package io.xrio.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xrio.gateway.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.xrio.gateway.utils.JwtUtils.ACCESS_TOKEN_ID;
import static io.xrio.gateway.utils.JwtUtils.REFRESH_TOKEN_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 26/9/2021
 */
@RequiredArgsConstructor
@Slf4j
public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username: {}", username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String accessToken = JwtUtils.createAccessToken(user);
        String refreshToken = JwtUtils.createRefreshToken(user);
        Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN_ID, accessToken);
        tokens.put(REFRESH_TOKEN_ID, refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
