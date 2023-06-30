package com.example.spring_study.Service;

import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Repository.ScheduleRepository;
import com.example.spring_study.DTO.ClickDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public List<Schedule> getSchedule() {
        System.out.println("실행1");
        return scheduleRepository.findAllByUser_emailAndCreateDate("111@naver.com", LocalDate.now());
    }
    public List<Schedule> getSchedule(ClickDate date) {
        System.out.println("실행2");
        return scheduleRepository.findAllByUser_emailAndCreateDate("111@naver.com", LocalDate.of(date.getYear(), date.getMonth(),date.getDay()));
    }
}
