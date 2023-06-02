package com.example.spring_study.Enum;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AchieveEnum {
    @JsonProperty("달성") achieve,
    @JsonProperty("쉼") rest,
    @JsonProperty("달성중") doing,
    @JsonProperty("실패") fail
}