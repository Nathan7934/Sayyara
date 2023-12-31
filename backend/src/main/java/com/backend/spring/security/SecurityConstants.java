package com.backend.spring.security;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.HashSet;
import java.util.Set;

public class SecurityConstants {
    private static final String SECRET = "3EAA622B1A2FDF9EB575EDCF11436";
    public static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET.getBytes());
    public static final int EXPIRATION_TIME_SHORT = 10 * 60 * 1000; // 10 minutes
    public static final int EXPIRATION_TIME_LONG = 7 * 24 * 60 * 60 * 1000; // 1 week
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String LOGIN_URL = "/user/login";

    public static final Set<String> SHOP_OWNER_ROUTES = new HashSet<>(Set.of(
            "/appointments"
    ));

}
