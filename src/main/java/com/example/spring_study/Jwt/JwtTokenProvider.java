package com.example.spring_study.Jwt;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

// Custom Provider 생성
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserService userService;
    @Value("${jwt.secretKey}")
    private String secretkey;
    private static long expireTimeMs = 1000 * 10;

    // Jwt 토큰 생성
    public String createToken(String user_email){
        // Claim = Jwt Token에 들어갈 정보
        Claims claims = Jwts.claims();
        // Claim에 user_email을 넣어 줌으로써 나중에 user_email을 꺼낼 수 있음
        claims.put("user_email", user_email);
        // Jwt payload에 저장되는 정보 단위, 보통 여기서 user를 식별하는 값을 넣는다.
        return Jwts.builder()
                .setClaims(claims)  // 정보 저장
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 토큰 발행 시간 정보
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))  // 토큰 유효시간
                .signWith(SignatureAlgorithm.HS256, secretkey)  // 사용할 암호화 알고리즘과 signature에 들어갈 secret값
                .compact();  // si
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

    public User getUserByEmail(String user_email) {
        return userService.getUserByEmail(user_email);
    }
}
