package com.example.spring_study.Converter;

import com.example.spring_study.Enum.GenderEnum;
import org.springframework.core.convert.converter.Converter;

//  커스텀 컨버트 (JSON객체가 아닌 파라미터로 전달받은 값을 변환하기 위한 코드)
public class GenderEnumConverter implements Converter<String, GenderEnum> {
    @Override
    public GenderEnum convert(String source){
        try {
            if(source.equals("남자")){
                return GenderEnum.M;
            }else if(source.equals("여자")){
                return GenderEnum.F;
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
