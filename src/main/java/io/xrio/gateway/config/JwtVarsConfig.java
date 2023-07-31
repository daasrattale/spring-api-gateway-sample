package io.xrio.gateway.config;

/**
 * @author : Elattar Saad
 * @version 1.0
 * @since 27/9/2021
 */
public class JwtVarsConfig {

    public static final String SECRET = "XRIO_SECRET_TOKEN";
    public static final String ISSUER = "XRIO";
    public static final long ACCESS_TOKEN_VALIDITY = 600000;
    public static final long REFRESH_TOKEN_VALIDITY = 604800000;
    public static final String BEARER = "Bearer "; // do not remove the space!
    public static final String CLAIMS_KEY = "ROLES";
    public static final String ACCESS_TOKEN_ID = "accessToken";
    public static final String REFRESH_TOKEN_ID = "refreshToken";

}
