package com.example.spring_study.Controller;

import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Model.KakaoOAuthToken;
import com.example.spring_study.Model.KakaoUserInfo;
import com.example.spring_study.Service.AuthService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 카카오 로그인 인증 callback url
    @GetMapping(value = "kakao/callback")
    @ResponseBody
    public ResponseDto kakaoCallback(String code){
        // 인가 권한 체크
        ResponseEntity<String> response = authService.getAuthorizeToken(code);

        // 권한 체크 후 인증 토큰만 추출
        String kakaoOAuthToken = authService.getAuthToken(response.getBody());

        // 사용자 정보 가져오기 (true = 신규회원 / false = 기존회원)
        User user = authService.getUserInfo(kakaoOAuthToken);

        // 신규, 기존 회원 체크
        String token = authService.joinOrLogin(user);

        return new ResponseDto(token);
    }
}
