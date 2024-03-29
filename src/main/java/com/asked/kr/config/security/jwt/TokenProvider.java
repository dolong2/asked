package com.asked.kr.config.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    enum TokenType{
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken");
        String value;
        TokenType(String value){
            this.value=value;
        }
    }
    enum TokenClaimName{
        USER_EMAIL("userEmail"),
        TOKEN_TYPE("tokenType");
        String value;
        TokenClaimName(String value) {
            this.value = value;
        }
    }
    private Key getSigningKey(String secretKey){
        byte keyByte[]=secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }
    public Claims extractAllClaims(String token) throws ExpiredJwtException, IllegalArgumentException, MalformedJwtException, SignatureException, UnsupportedJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String getUserEmail(String token){
        return extractAllClaims(token).get(TokenClaimName.USER_EMAIL.value,String.class);
    }
    public String getTokenType(String token){
        return extractAllClaims(token).get(TokenClaimName.TOKEN_TYPE.value,String.class);
    }
    public Boolean isTokenExpired(String token){
        try{
            extractAllClaims(token).getExpiration();
            return false;
        }
        catch (ExpiredJwtException e){
            return true;
        }
    }
    private String doGenerateToken(String userEmail, TokenType tokenType, long expireTime) {
        final Claims claims = Jwts.claims();
        //AccessToken일 떄 TokenClaim에 UserEmail을 추가한다.
        if(TokenType.ACCESS_TOKEN == tokenType)
            claims.put("userEmail", userEmail);
        claims.put("tokenType", tokenType.value);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateAccessToken(String email){
        return doGenerateToken(email, TokenType.ACCESS_TOKEN,ACCESS_TOKEN_EXPIRE_TIME);
    }
    public String generateRefreshToken(String email){
        return doGenerateToken(email, TokenType.REFRESH_TOKEN,REFRESH_TOKEN_EXPIRE_TIME);
    }

    public Long getRefreshTokenExpireTime(){
        return REFRESH_TOKEN_EXPIRE_TIME;
    }
}

