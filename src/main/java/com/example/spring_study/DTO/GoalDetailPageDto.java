package com.example.spring_study.DTO;

import com.example.spring_study.Entity.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDetailPageDto {
    private int remainingDays; // 목표 달성까지 남은 날짜
    private int percentage; // 달성률
    private Goal goal; // 목표
}
