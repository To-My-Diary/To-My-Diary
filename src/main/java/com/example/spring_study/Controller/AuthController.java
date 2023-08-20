package com.example.spring_study.Controller;

import com.example.spring_study.DTO.Response.ResponseDto;
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

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    // 카카오 로그인 인증 callback url
    @GetMapping(value = "kakao/callback")
    @ResponseBody
    public ResponseDto kakaoCallback(String code){
        System.out.println("카카오 인증완료");
        RestTemplate restTemplate = new RestTemplate();

//        Http Header 객체 생성
        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

//        Http Body 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "148570826c7770f175f7b4c40a87580e");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code",code);
//        Header와 Body를 HttpEntity객체에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params, header);

//        Http 요청
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        return new ResponseDto(response);
    }
}
