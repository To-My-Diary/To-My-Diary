package com.example.spring_study.Enum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum GenderEnum {
    @JsonProperty("남자") M,
    @JsonProperty("여자") F
}
