package com.example.spring_study.Service;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Exception.BaseException;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Model.KakaoOAuthToken;
import com.example.spring_study.Model.KakaoUserInfo;
import com.example.spring_study.Repository.UserRepository;
import com.example.spring_study.Security.CustomAuthenticationProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.spring_study.DTO.Response.ResponseStatus.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${kakao.auth.client-id}")
    private String client_id;
    @Value("${kakao.auth.redirect-uri}")
    private String redirect_uri;
    @Value("${kakao.auth.secret}")
    private String oauth_pw;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CustomAuthenticationProvider customAuthenticationProvider;
    public ResponseEntity<String> getAuthorizeToken(String code) {
        System.out.println("code"+code);
        RestTemplate restTemplate = new RestTemplate();
        try{
            //        Http Header 객체 생성
            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

//        Http Body 객체 생성
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", client_id);
            params.add("redirect_uri", redirect_uri);
            params.add("code",code);
//        Header와 Body를 HttpEntity객체에 담기
            HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params, header);

//        Http 요청 ( url, 요청방식, Http객체, 반환 형태 )
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );
            return response;

        }catch(Exception e){
            e.printStackTrace();
            throw new BaseException(DUPLI_AUTHORIZATION_CODE);
        }
    }

    public String getAuthToken(String body) {
        ObjectMapper objectMapper = new ObjectMapper();

        KakaoOAuthToken kakaoOAuthToken = null;
        try{
            kakaoOAuthToken = objectMapper.readValue(body, KakaoOAuthToken.class);
        }catch (JsonMappingException e){
            throw new BaseException(FAIL_JSON_MAPPING);
        }catch (JsonProcessingException e){
            throw new BaseException(FAIL_JSON_PROCESS);
        }
        return kakaoOAuthToken.getAccess_token();
    }

    public User getUserInfo(String kakaoOAuthToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        header.add("Authorization", "Bearer "+kakaoOAuthToken);

        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity(header);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoUserInfo kakaoUserInfo = null;
        try{
            kakaoUserInfo = objectMapper.readValue(response.getBody(), KakaoUserInfo.class);
        }catch(JsonMappingException e){
            throw new BaseException(FAIL_JSON_MAPPING);
        }catch (JsonProcessingException e){
            throw new BaseException(FAIL_JSON_PROCESS);
        }

        String email = kakaoUserInfo.getKakao_account().getEmail();
        String name = kakaoUserInfo.getKakao_account().getProfile().getNickname();

        User user = User.builder()
                .name(name)
                .email(email)
                .pw(bCryptPasswordEncoder.encode(oauth_pw))
                .role("ROLE_USER")
                .build();

        return user;
    }
    public String joinOrLogin(User user) {

        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
            userRepository.save(user);
        }

        return login(user);
    }
    public String login(User user){
        String token = "";
        try{
            Authentication authentication = customAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), oauth_pw));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            token =  jwtTokenProvider.createToken(authentication.getName(), authentication.getCredentials().toString());
        }catch (Exception e){
            throw new BaseException(LOGIN_FAIL);
        }


        return token;
    }
}
