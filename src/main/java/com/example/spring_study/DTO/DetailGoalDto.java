package com.example.spring_study.DTO;

import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Enum.AchieveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailGoalDto {
    private Long detailGoalId; // 목표 상세 번호 (기본키)
    private Long goalId; // 부모 목표 번호 (외래키)
    private String content; // 내용
    private AchieveEnum achieve; // 달성 여부
    private LocalDate planDate; // 목표 날짜
}
