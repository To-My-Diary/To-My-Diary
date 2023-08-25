package com.example.spring_study.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarGoalDto {
    private boolean isMainGoal;
    private String color;
    private int date;
}
