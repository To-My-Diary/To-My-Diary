package com.example.spring_study.Jwt;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Exception.BaseException;
import com.example.spring_study.Repository.UserRepository;
import com.example.spring_study.Security.CustomUserDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.spring_study.DTO.Response.ResponseStatus.FAIL_AUTH_JWT;
import static com.example.spring_study.DTO.Response.ResponseStatus.INVALID_JWT;

// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
// 해당 클래스는 Spring Security의 환경설정을 구성하는 단계에서 필터로 등록한 클래스이다.
// 지정한 URL별 JWT유효성 검증을 수행하며 직접적인 사용자 '인증'은 확인한다.
public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("jwtTokenFilter");
        String token = request.getHeader("Authorization");
        try{
        if(token == null || !token.startsWith("Bearer ")){
            throw new BaseException(INVALID_JWT);
        }
        token = token.replace("Bearer ","");

        String email = jwtTokenProvider.getUserEmail(token);
        String password = jwtTokenProvider.getUserPassword(token);
        System.out.println(email +" / "+password);

            if(email != null){
                User user = userRepository.findByEmail(email).get();
                CustomUserDetails userDetails = new CustomUserDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (BaseException e){
            throw new BaseException(INVALID_JWT);
        } catch(Exception e){
            e.printStackTrace();
            throw new BaseException(FAIL_AUTH_JWT);
        }
        filterChain.doFilter(request,response);
    }
}
