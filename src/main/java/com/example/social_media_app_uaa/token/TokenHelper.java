package com.example.social_media_app_uaa.token;
import com.example.social_media_app_uaa.entity.UserEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenHelper {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public static String generateToken(UserEntity userEntity) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userEntity.getId());
        claims.put("username", userEntity.getUsername());

    }

    public static Long getUserIdFromToken(String token) {
        token = token.substring(7);
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("user_id", Long.class);
    }
}
