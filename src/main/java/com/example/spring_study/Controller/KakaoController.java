//package com.example.spring_study.Controller;
//
//import com.example.spring_study.Service.KakaoService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import groovy.util.logging.Slf4j;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import javax.validation.constraints.Null;
//import java.net.http.HttpHeaders;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//public class KakaoController {
//    private final KakaoService kakaoService;
//
//    @GetMapping("/callback")
//    public String kakaoCallback(String code) { //@ResponseBody : Data를 리턴해주는 컨트롤러 함수
//
//        //POST 방식으로 key=value 데이터를 요청 (카카오 쪽으로)
//        RestTemplate rt = new RestTemplate();
//
//        //HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "code");
//        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
//
//        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(params, headers);
//
//        //Http요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
//        ReponseEntity<String> response = rt.exchange("https://kauth.kakao.com.oauth/token",
//                HttpMethod.POST, //요청 메서드
//                kakaoRequest, //데이터
//                String.class // 응답 받을 타입
//        );
//
//        //Gson, Json, Simple, ObjectMapper
//        ObjectMapper objectMapper = new ObjectMapper();
//        OAuthToken oAuthToken = null;
//
//        try {
//            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
