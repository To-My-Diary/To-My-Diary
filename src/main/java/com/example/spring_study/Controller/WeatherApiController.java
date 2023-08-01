package com.example.spring_study.Controller;

import com.example.spring_study.DTO.OpenApiDto;
import com.example.spring_study.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherApiController {
    // Open Weather API KEY
    @Value("${weatherAPI.key}")
    private String  apiKey;

    // Open Weather API 응답 데이터 중 날씨 조회
    @GetMapping("/weather/api")
    public Object getWeatherData(){
        System.out.println("getWeather");
        // 주석 코드는 클라이언트에게 위도, 경도 값을 받아서 지역에 맞는 날씨 데이터를 조회하는 코드
        // String BASE_URL = String.format("https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=%s&lang=kr",apiKey);
        // 아래 코드는 우리나라 전체에 대한 날씨 데이터를 조회하는 코드
        String BASE_URL = String.format("https://api.openweathermap.org/data/2.5/weather?q=Koesan&appid=%s&lang=kr&units=metric",apiKey);

        RestTemplate restTemplate = new RestTemplate();

        OpenApiDto response = restTemplate.getForObject(BASE_URL, OpenApiDto.class);

        return response.getWeather();
    }
}
