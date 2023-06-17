package com.example.spring_study.Service;

import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public List<Schedule> getTodaySchedule() {
        return scheduleRepository.findAllByUser_emailAndCreateDate("111@naver.com", LocalDate.now());
    }
}
