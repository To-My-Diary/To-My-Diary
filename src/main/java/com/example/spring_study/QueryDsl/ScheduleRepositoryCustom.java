package com.example.spring_study.QueryDsl;

import com.example.spring_study.Entity.Schedule;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> getSchedules(String name);

    List<Schedule> getUniqueSchedules(String email, LocalDate date);
}
