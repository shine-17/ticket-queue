package study.ticket.ticket_queue.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;


public class JwtProvider {
    private final SecretKey key;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtProvider(String secret, long accessTokenExpiration, long refreshTokenExpiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public JwtToken generateToken(String userId, List<String> roles) {
        long now = System.currentTimeMillis();

        Date date = new Date(now);

        String accessToken = Jwts.builder()
                .subject(userId)
                .claim("roles", roles)
                .issuedAt(date)
                .expiration(new Date(now + accessTokenExpiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .subject(userId)
                .issuedAt(date)
                .expiration(new Date(now + refreshTokenExpiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        return new JwtToken(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String userId = payload.getSubject();

        List<String> roles = payload.get("roles", List.class);

        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(userId, token, authorities);
    }

    public String getUserId(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
