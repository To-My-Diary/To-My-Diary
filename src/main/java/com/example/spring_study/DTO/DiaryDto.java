package com.example.spring_study.DTO;
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
}
