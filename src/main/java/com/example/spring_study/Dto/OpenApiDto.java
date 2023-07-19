package com.example.spring_study.Dto;

import lombok.Data;
import java.util.List;

// Open Weather API에 대한 DTO 객체
@Data
public class OpenApiDto {

    // 날씨 조건 ID, 매개변수 그룹, 아이콘 ID 등 기상조건 코드 추가정보
    private List<WeatherDto> weather;
}
