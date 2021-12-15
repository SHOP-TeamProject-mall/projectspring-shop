package com.example.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

    private final String SECRETKEY = "f123ekjkf13e4423j5fe";

    // 정보 추출용 메소드
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    // 토큰 생성(아이디 정보를 이용한 토큰 생성)
    public String generateToken(String memberid) {
        long tokenValidTime = 1000 * 60 * 60 * 24;
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder().setClaims(claims).setSubject(memberid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRETKEY).compact();
        return token;
    }

    // 토큰 유효성 확인
    public Boolean validateToken(String token, String id) {
        final String name = this.extractUsername(token);
        if (name.equals(id) && !isTokenExpired(token)) {
            return true;
        }
        return false;
    }

    // 토큰에서 아이디 정보 추출하기
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰에서 만료 시간 추출하기
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰의 만료시간이 유효한지 확인
    public Boolean isTokenExpired(String token) {

        return this.extractExpiration(token).before(new Date());
    }
}
