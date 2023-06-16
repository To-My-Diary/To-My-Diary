package com.example.spring_study.Enum;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EmotionEnum {
    @JsonProperty("기쁨") happy,
    @JsonProperty("슬픔") sad,
    @JsonProperty("우울") depressed,
    @JsonProperty("화남") unpleasant
}