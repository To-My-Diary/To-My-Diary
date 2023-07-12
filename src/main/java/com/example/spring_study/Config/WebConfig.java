package com.example.spring_study.Config;

import com.example.spring_study.Converter.GenderEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //  커스텀 컨버트를 구현하기 위해 WebMvcConfigurer를 구현하고 addFormatters를 재정의
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GenderEnumConverter());
    }
}
