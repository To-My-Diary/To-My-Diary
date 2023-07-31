package com.example.spring_study.Security;

import com.example.spring_study.DTO.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


// 이메일과 비밀번호 기반의 데이터를 Form 데이터로 전송 받아 인증을 담당하는 필터
// 사용자 요청 정보로 UsernamePasswordAuthenticationToken 발급 후 AuthenticationManager에게 전달하고
// AuthenticationProvider의 인증 메소드(authencate)를 실행하는 필터
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Value("${jwt.secretKey}")
    private String secretKey;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        super.setAuthenticationManager(authenticationManager);
    }
    // attemptAuthentication 인증이 정상적으로 이루어졌다면 해당 메소드 실행된다.
    // 즉, JWT토큰을 만들어서 request 요청한 사용자에게 reponse해주면된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("로그인성공");
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        Claims claims = Jwts.claims().setSubject(customUserDetails.getUsername());
        String jwtToken = Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + (1000*10)))
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        System.out.println(jwtToken);

        response.addHeader("Authorization", "Bearer "+jwtToken);
    }

    // 인증용 객체를 AuthenticationManager 구현체인 CustomAuthentication Provider에게 전달한다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authRequest;
        try{
            authRequest = getAuthRequest(request);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        Authentication authentication = getAuthenticationManager().authenticate(authRequest);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(customUserDetails.getUser().getEmail());
        System.out.println(customUserDetails.getUser().getPw());

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    // 클라이언트로부터 받은 input값 User객체 매핑 후 UsernamePasswordAuthenticationToken 생성
    // 즉, CustomAuthenticationFilter가 요청을 가로채서, 객체로 매핑하고 인증용 토큰(UsernamePasswordAuthenticationToken)을 생성한다.
    public UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws Exception{
        try{
            ObjectMapper om = new ObjectMapper();
            LoginDto loginDto = om.readValue(request.getInputStream(), LoginDto.class);

            return new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPw());
        }catch(UsernameNotFoundException e){
            throw new UsernameNotFoundException(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage(), e.getCause());
        }
    }
}
