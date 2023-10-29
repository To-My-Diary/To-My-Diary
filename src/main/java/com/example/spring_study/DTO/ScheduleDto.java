package com.example.spring_study.DTO;

import com.example.spring_study.Enum.AchieveEnum;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private Long scheduleId; // 일정 번호
    private String msg; // 내용
    private AchieveEnum achieve; // 달성 여부
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate planDate; // 목표 날짜 + 시간
    private String userId; // 사용자 (작성자)
}
