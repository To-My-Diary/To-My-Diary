package com.example.spring_study.Security;

import com.example.spring_study.DTO.LoginDto;
import com.example.spring_study.Service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

// 전달 받은 사용자의 아이디와 비밀번호를 기반으로 비즈니스 로직을 처리하여 사용자의 인증에 대해서 검증을 수행하는 클래스
// CustomAuthenticationFilter로 부터 생성한 토큰을 통하여 UserDetailService를 통해 DB 내에서 정보를 조회합니다.
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @NonNull
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String user_email = token.getName();

        String password = (String) token.getCredentials();

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(user_email);

        if(!(customUserDetails.getUsername().equals(user_email) && bCryptPasswordEncoder.matches(password, customUserDetails.getPassword()))){
            System.out.println("Provider 실패");
            throw new BadCredentialsException((customUserDetails.getUsername() + "Invalid password"));
        }
        return new UsernamePasswordAuthenticationToken(customUserDetails, password, customUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
