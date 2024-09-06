package com.example.springIs0902.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    // 토큰 비밀키 (이 값은 노출되지 않도록 환경 변수 등에서 관리해야 함)
    private String secret = "mysecretkey";

    // 토큰의 유효시간 (예: 10시간)
    public static final long JWT_TOKEN_VALIDITY = 10 * 60 * 60;

    // JWT에서 특정 클레임 추출
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // JWT에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // JWT에서 만료 날짜 추출
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    // JWT에서 모든 클레임 추출 (토큰 검증할 때 필요)
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT가 만료되었는지 확인
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // JWT 생성
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    // JWT 생성하는 실제 메서드 (Header, Payload, Signature 처리)
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)  // subject는 보통 사용자 정보
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))  // 만료 시간
                .signWith(SignatureAlgorithm.HS512, secret)  // 서명 알고리즘과 비밀키
                .compact();
    }

    // JWT가 유효한지 확인 (사용자 이름과 만료 여부 체크)
    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token));
    }
}
