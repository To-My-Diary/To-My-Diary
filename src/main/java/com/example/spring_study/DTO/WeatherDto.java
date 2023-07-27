package com.example.spring_study.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
    /** 기상 조건 ID */
    private int id;

    /** 날씨 매개 변수 그룹 (비, 눈, 극한 등) */
    private String main;

    /** 그룹 내 날씨 조건 */
    private String description;

    /** 날씨 아이콘 ID */
    private String icon;
}
