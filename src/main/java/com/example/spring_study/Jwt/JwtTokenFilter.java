package com.example.spring_study.Jwt;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Service.UserService;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header의 Authorization의 값이 비어있다면 -> Jwt Token을 전송하지 않는다 -> 로그인 안함
        if(authorizationHeader == null){
            filterChain.doFilter(request,response);
            return;
        }

        // Header의 Authorization의 값이 'Bearer'로 시작하지 않으면 -> 잘못된 토큰
        if(!authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분 (Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];

        // 전송받은 Jwt Token이 만료되었다면 -> 다음필터 진행 (인증 x)
        if(JwtTokenProvider.isExpired(token, secretKey)){
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt Token에서 user_email 가져오기
        String user_email = JwtTokenProvider.getUserEmail(token, secretKey);

        // 가져온 user_email로 User 가져오기
        User loginUser = jwtTokenProvider.getUserByEmail(user_email);

        // loginUser 정보로 UsernamePasswordAuthentocationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getEmail(),
                null, null);

        //  권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}