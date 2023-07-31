package com.example.spring_study.Config;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Jwt.JwtTokenFilter;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Security.CustomUserDetailsService;
import com.example.spring_study.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;




//Spring Security 환경설정을 구성하기 위한 클래스이다.
//웹 서브시가 로드 될 때 Spring Container의해 관리가 되는 클래스이며 사용자에 대한 인증과 인가에 대한 구성을 Bean 메소드로 주입을 한다.
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.
        http.csrf().disable();
        // 일반적인 루트가 아닌 다른 방식으로 요청시 거절,
        // header에 id, pw가 아닌 token(jwt)을 달고 간다.
        // 그래서 basic이 아닌 bearer를 사용한다.
        http.httpBasic().disable()
                // 토큰에 저장된 유저정보를 사용해야하기 때문에 CustomUserDetailsService 클래스를 생성한다.
                // 세션기반의 인증기반을 사용하지 않는다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // JwtAuthenticationFilter를 UsernamePasswordAuthentocationFilter 전에 넣는다.
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/index**").authenticated()
                .anyRequest().permitAll();
        return http.build();
    }
}
