package study.ticket.ticket_queue.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import study.ticket.ticket_queue.domain.WaitingQueueTokenPayload;
import study.ticket.ticket_queue.util.JsonHelper;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenTest {

    @Value("${spring.jwt.secret}")
    private String key;
    @Value("${spring.jwt.accessExpiration}")
    private long ACCESS_TOKEN_EXPIRATION;
    @Value("${spring.jwt.refreshExpiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    private final String userId = "test1";
    private final long showId = 1L;

    private JwtProvider jwtProvider;
    private WaitingQueueTokenPayload payload;

    @BeforeAll
    void setUp() {
//        payload = new WaitingQueueTokenPayload(userId);
        jwtProvider = new JwtProvider(key, ACCESS_TOKEN_EXPIRATION, REFRESH_TOKEN_EXPIRATION);
    }

    @Test
    @DisplayName("Secret Key 생성")
    void EncryptSecretKey() {
        System.out.println(key);

        byte[] bytes = Base64.getDecoder().decode(key);
        String secret = Base64.getEncoder().encodeToString(bytes);
        System.out.println(secret);
    }

    @Test
    @DisplayName("Jwt 생성")
    void generateJwtToken() {
        JwtToken jwtToken = jwtProvider.generateToken(userId, List.of());

        assertThat(jwtToken).isNotNull();
    }

    @Test
    @DisplayName("AccessToken 검증 성공")
    void succeedVerifyAccessToken() {
        JwtToken jwtToken = jwtProvider.generateToken(userId, List.of());

        boolean result = jwtProvider.validateToken(jwtToken.accessToken());
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("RefreshToken 검증 성공")
    void succeedVerifyRefreshToken() {
        JwtToken jwtToken = jwtProvider.generateToken(userId, List.of());

        boolean result = jwtProvider.validateToken(jwtToken.refreshToken());
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Jwt 검증 실패 - Secret key 불일치")
    void failedVerifyToSecretKey() {
        String key = this.key + " ";

        JwtProvider jwtProvider = new JwtProvider(key, ACCESS_TOKEN_EXPIRATION, REFRESH_TOKEN_EXPIRATION);
        JwtToken jwtToken = jwtProvider.generateToken(userId, List.of());

        boolean accessResult = jwtProvider.validateToken(jwtToken.accessToken());
        boolean refreshResult = jwtProvider.validateToken(jwtToken.refreshToken());

        assertThat(accessResult).isTrue();
        assertThat(refreshResult).isTrue();
    }

    @Test
    @DisplayName("Jwt 검증 실패 - 기간 만료")
    void failedVerifyToExpiration() {
        LocalDateTime time = LocalDateTime.now().minusDays(1);

        Date date = Timestamp.valueOf(time);
        Date accessDate = Timestamp.valueOf(time.plusHours(1));
        Date refreshDate = Timestamp.valueOf(time.plusHours(2));

        JwtToken targetToken = generateToken(key, date, accessDate, refreshDate);

        boolean accessResult = jwtProvider.validateToken(targetToken.accessToken());
        boolean refreshResult = jwtProvider.validateToken(targetToken.refreshToken());

        assertThat(accessResult).isFalse();
        assertThat(refreshResult).isFalse();
    }


    private JwtToken generateToken(String key, Date date, Date accessDate, Date refreshDate) {
        String subject = JsonHelper.toJson(payload);
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());

        String accessToken = Jwts.builder()
                .subject(subject)
                .claim("roles", List.of())
                .issuedAt(date)
                .expiration(accessDate)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .subject(subject)
                .issuedAt(date)
                .expiration(refreshDate)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

        return new JwtToken(accessToken, refreshToken);
    }


}
