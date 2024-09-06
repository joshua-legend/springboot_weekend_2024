package com.example.springIs0902.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey = "your-secret-key"; // 비밀 키 (외부 설정으로 빼는 것이 좋음)
    private final long valid = 10000; // 1시간

    // JWT 토큰 생성
    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role); // 역할 정보 추가
        Date now = new Date();
        Date validity = new Date(now.getTime() + valid);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명 알고리즘 설정 및 비밀 키 사용
                .compact();
    }

    // 토큰에서 사용자 이름 추출
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰에서 역할(Role) 정보 추출
    public String getRole(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // JWT가 유효하지 않거나 만료된 경우 처리
            return false;
        }
    }

    // 요청 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
