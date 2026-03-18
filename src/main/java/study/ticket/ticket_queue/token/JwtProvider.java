package study.ticket.ticket_queue.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import study.ticket.ticket_queue.domain.TokenPayload;
import study.ticket.ticket_queue.util.JsonHelper;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtProvider {

    private final SecretKey key;
    private final long accessTokenExpiration;

    public JwtProvider(@Value("${spring.jwt.secret}") String secret,
                       @Value("${ticket.booking.token.active}") long activeToken,
                       @Value("${ticket.booking.token.waiting}") long waitingToken) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpiration = activeToken;
    }

    public String generateToken(TokenPayload payload) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
//        String subject = JsonHelper.toJson(payload);
        String data = JsonHelper.toJson(payload);

        return Jwts.builder()
                .subject(data)
                .issuedAt(date)
                .expiration(new Date(now + accessTokenExpiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

//        String refreshToken = Jwts.builder()
//                .subject(data)
//                .issuedAt(date)
//                .expiration(new Date(now + refreshTokenExpiration))
//                .signWith(key, Jwts.SIG.HS256)
//                .compact();

//        return new JwtToken(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid Jwt");
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
