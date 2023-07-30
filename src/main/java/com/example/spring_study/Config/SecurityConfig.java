package com.example.spring_study.Config;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Jwt.JwtTokenFilter;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Security.CustomUserDetailsService;
import com.example.spring_study.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${Jwt.secretKey}")
    private static String secretKey;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        // 일반적인 루트가 아닌 다른 방식으로 요청시 거절,
        // header에 id, pw가 아닌 token(jwt)을 달고 간다.
        // 그래서 basic이 아닌 bearer를 사용한다.
        http.httpBasic().disable()
                // 토큰에 저장된 유저정보를 사용해야하기 때문에 CustomUserDetailsService 클래스를 생성한다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // JwtAuthenticationFilter를 UsernamePasswordAuthentocationFilter 전에 넣는다.
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/index**").authenticated()
                .anyRequest().permitAll();
        return http.build();
    }
}
