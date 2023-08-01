package com.example.spring_study.Config;

import com.example.spring_study.Jwt.JwtTokenFilter;
import com.example.spring_study.Jwt.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests()
                .requestMatchers(
                        new AntPathRequestMatcher("/weather/api**")).hasAuthority("ROLE_USER")
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
                .addFilter(customAuthenticationFilter())
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
                // Spring Security Custom Filter 적용 - Form '인증'에 대해서 적용


        return http.build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter(userRepository);
    }

    // authenticate의 인증 메서드를 제공하는 매니저로 Provider의 인터페이스를 의미
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(customAuthenticationProvider());
    }

    // 인증 제공자로 사용자의 이름과 비밀번호가 요구된다.
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider(bCryptPasswordEncoder);
    }

    // 커스텀을 수행한 인증 필터로 접근 URL, 데이터 전달방식 등 인증과정 및 인증 후 처리에 대한 설정 구성하는 메소드
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(){
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/doLogin");
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }
}
