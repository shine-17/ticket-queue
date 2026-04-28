package study.ticket.ticket_queue.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import study.ticket.ticket_queue.filter.JwtAuthenticationFilter;
import study.ticket.ticket_queue.token.JwtProvider;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtProvider jwtProvider;

    /**
     * CORS 설정을 위한 Bean 등록
     * - 프론트엔드(React 등)에서 API 요청 시 CORS 문제 해결
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080")); // 허용할 도메인
            configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 HTTP 메서드 허용
            configuration.setAllowCredentials(true); // 인증 정보 포함 허용
            configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
            configuration.setExposedHeaders(Collections.singletonList("Authorization")); // Authorization 헤더 노출
            configuration.setMaxAge(3600L); // 1시간 동안 캐싱
            return configuration;
        };
    }

    /**
     * Spring Security 필터 체인 설정
     * - JWT 인증을 기반으로 한 보안 설정 적용
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .csrf(AbstractHttpConfigurer::disable) // Jwt 사용 시 CSRF 보호 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 폼 비활성화 (Jwt 사용)
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화

                // 엔드포인트 별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/", "/signup", "/api").permitAll()
                        .requestMatchers("/admin").hasAuthority("ADMIN")
                        .anyRequest().authenticated())

                // JWT 필터 추가 (기존 UsernamePasswordAuthenticationFilter 이전에 실행)
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

                // 세션을 사용하지 않음 (JWT 기반 인증이므로 STATELESS 모드 설정)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
