package com.example.spring_study.Jwt;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Exception.NotFoundUserException;
import com.example.spring_study.Repository.UserRepository;
import com.example.spring_study.Security.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
// 해당 클래스는 Spring Security의 환경설정을 구성하는 단계에서 필터로 등록한 클래스이다.
// 지정한 URL별 JWT유효성 검증을 수행하며 직접적인 사용자 '인증'은 확인한다.
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.secretKey}")
    private String secretKey;
    private UserRepository userRepository;

    public JwtTokenFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("jwtfilter 실행");
        String jwtHeader = request.getHeader("Authorization");

        if(jwtHeader == null || !jwtHeader.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        System.out.println(token);
        String email = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

        if(email != null){
            User user = userRepository.findByEmail(email).get();

            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,customUserDetails.getPassword(),customUserDetails.getAuthorities());
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request,response);
    }
}
