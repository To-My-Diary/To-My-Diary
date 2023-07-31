package com.example.spring_study.Jwt;

import com.example.spring_study.DTO.LoginDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Service.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Custom Provider 생성
// 해당 클래스는 JWT에서 사용되는 토큰 관련 유틸들을 관리하는 클래스입니다.
//JWT를 생성하거나 유효성을 체크하는 등의 전반적으로 처리되는기능들을 모아둔 클래스입니다.class
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserService userService;
    @Value("${jwt.secretKey}")
    private String secretkey;

    private static long expireTimeMs = 1000 * 10;

    // Jwt 토큰 생성 메소드
    public String createToken(LoginDto userDto){
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(userDto))
                .setSubject(String.valueOf(userDto.getEmail()))
                .signWith(SignatureAlgorithm.HS256, createSignature())
                .setExpiration(createExpiredDate());
        return builder.compact();
    }

    // 1. Header값 생성해주는 메소드
    private Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate",System.currentTimeMillis());
        return header;
    }

    // 2. 사용자 정보를 기반으로 클래임 생성 메소드
    private Map<String, Object> createClaims(LoginDto userDto) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("user_email", userDto.getEmail());
        claims.put("password", userDto.getPw());
        return claims;
    }

    // 3. JWT 서명 발급해주는 메소드
    private Key createSignature() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretkey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // 4. 토큰의 만료기간의 지정하는 메소드
    private Date createExpiredDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 8);
        return c.getTime();
    }

    // Claims에서 user_email 꺼내기
    public String getUserEmail(String token){
        return extractClaims(token, secretkey).get("user_email").toString();
    }

    // 발급된 Token이 만료 시간이 지났는지 체크
    public boolean isExpired(String token){
        Date expiredDate = extractClaims(token, secretkey).getExpiration();
        // Token 의 만료날까지 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }

    // SecretKey를 통해서 Token Parsing
    public static Claims extractClaims(String token,String secretkey){
        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
    }
    // Repository에서 user_email꺼내기
    public User getUserByEmail(String user_email) {
        return userService.getUserByEmail(user_email);
    }
}
