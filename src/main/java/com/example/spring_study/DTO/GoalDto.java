package com.example.spring_study.DTO;

import com.example.spring_study.Entity.DetailGoal;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.AchieveEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {
    private Long goalId; // 기본키
    private String content; // 목표 내용
    private AchieveEnum achieve; // 달성 여부
    private int achieveRate; // 달성률
    private LocalDate planDate; // 목표 날짜
    private String color; // 색깔
    private String userId; // 사용자
    private List<DetailGoalDto> detailGoals = new ArrayList<>(); // 상세 목표 리스트
}
