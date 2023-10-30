package com.example.spring_study.QueryDsl;

import com.example.spring_study.Entity.Schedule;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> getSchedules(String name);
}
