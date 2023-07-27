package com.example.spring_study.Dto;

import com.example.spring_study.Enum.EmotionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
    private Long diaryId;
    private String subject;
    private String content;
    private String userId;
    private EmotionEnum emotion;
    private String img;

    public DiaryDto(Object diaryId, Object subject, Object content, Object userId, Object emotion, Object img) {
        this.diaryId = (Long) diaryId;
        this.subject = (String) subject;
        this.content = (String) content;
        this.userId = (String) userId;
        this.emotion = (EmotionEnum) emotion;
        this.img = (String) img;
    }
}
