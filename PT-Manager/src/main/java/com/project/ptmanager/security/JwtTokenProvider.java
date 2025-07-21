package com.project.ptmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  public static final String KEY_ROLE = "role";
  public static final long EXPIRATION_TIME = 3600 * 1000; // 1시간

  private final CustomUserDetailsService customUserDetailsService;

  @Value("${jwt.secret}")
  private String secret; // TODO: 외부에서 비밀키 설정
  private final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

  public String generateToken(String username, String role) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

    return Jwts.builder()
        .subject(username)
        .claim(KEY_ROLE, role)
        .issuedAt(now)
        .expiration(expiration)
        .signWith(secretKey)
        .compact();
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      return false;
    }

    Claims claims = parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  private Claims parseClaims(String token) {
    JwtParser parser = Jwts.parser().verifyWith(secretKey).build();

    try {
      return parser.parseSignedClaims(token).getPayload();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
