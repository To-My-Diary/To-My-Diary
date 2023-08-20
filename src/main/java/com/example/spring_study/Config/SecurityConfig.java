package com.example.spring_study.Config;

import com.example.spring_study.Jwt.JwtTokenFilter;
import com.example.spring_study.Jwt.jwtExceptionFilter;
import com.example.spring_study.Repository.UserRepository;
import com.example.spring_study.Security.CustomAuthenticationFilter;
import com.example.spring_study.Security.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;




//Spring Security 환경설정을 구성하기 위한 클래스이다.
//웹 서브시가 로드 될 때 Spring Container의해 관리가 되는 클래스이며 사용자에 대한 인증과 인가에 대한 구성을 Bean 메소드로 주입을 한다.
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> {
            web.ignoring().antMatchers("/","/users/login","/users/join", "/swagger-ui/**","/v3/api-docs/**");
        };
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
                .requestMatchers(
                        new AntPathRequestMatcher("/weather/api**")).hasRole("USER")
                .requestMatchers(
                        new AntPathRequestMatcher("/users/test")).hasRole("USER")
                .anyRequest().permitAll()
                .and()
                // 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.
                .csrf().disable()
                // form 기반 로그인 방식을 비활성화하여 커스텀 필터를 사용
                .formLogin().disable()
                .httpBasic().disable()
                // 세션기반의 인증기반을 사용하지 않는다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(userRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new jwtExceptionFilter(), JwtTokenFilter.class);
                // Spring Security Custom Filter 적용 - Form '인증'에 대해서 적용


        return http.build();
    }
}
