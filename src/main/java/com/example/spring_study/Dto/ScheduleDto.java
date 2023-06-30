package com.example.spring_study.Dto;

import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Enum.AchieveEnum;
import com.example.spring_study.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private Long scheduleId; // 일정 번호
    private String msg; // 내용
    private AchieveEnum achieve; // 달성 여부
    private LocalDateTime planDate; // 목표 날짜 + 시간
    private String userId; // 사용자 (작성자)
}
