package com.example.social_media_app_uaa.security;
import com.example.social_media_app_uaa.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

// https://chatgpt.com/c/670be4e8-5540-8010-ace0-5d8035740fe0
// https://chatgpt.com/c/670be004-1844-8010-ad25-912561dce36f
@AllArgsConstructor
@Component
public class TokenHelper {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String generateToken(UserEntity userEntity) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .expiresAt(Instant.ofEpochSecond(expirationDate.getTime()))
                .issuedAt(Instant.ofEpochSecond(now.getTime()))
                .subject(userEntity.getUsername())
                .claim("user_id", userEntity.getId())
                .claim("username", userEntity.getUsername())
                .claim("full_name", userEntity.getFullName())
                .claim("image_url", userEntity.getImageUrl())
                .build();

        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims));
        return jwt.getTokenValue(); // Return the JWT token string

//        return Jwts.builder()
//                .claim("user_id", userEntity.getId())
//                .claim("username", userEntity.getUsername())
//                .setSubject(userEntity.getUsername())
//                .setIssuedAt(now)
//                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                .compact();
    }

    public Long getUserIdFromToken(String token) {
        token = token.substring(7);
        Jwt decodedJwt = jwtDecoder.decode(token);
        // Extract the user_id claim
        return decodedJwt.getClaim("user_id");
    }
}
