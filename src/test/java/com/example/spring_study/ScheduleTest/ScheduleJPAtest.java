package com.example.spring_study.ScheduleTest;

import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.AchieveEnum;
import com.example.spring_study.Enum.GenderEnum;
import com.example.spring_study.Repository.ScheduleRepository;
import com.example.spring_study.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class ScheduleJPAtest {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;
    @Test
    void createData(){
        for(int i =0; i < 5; i++){
            User user = userRepository.findByEmail("111@naver.com").get();
            Schedule sch = Schedule.builder()
                    .msg("내용"+i)
                    .achieve(AchieveEnum.doing)
                    .user(user)
                    .build();
            scheduleRepository.save(sch);
        }
        for(int i =0; i < 5; i++){
            User user = userRepository.findByEmail("222@naver.com").get();
            Schedule sch = Schedule.builder()
                    .msg("내용"+i)
                    .achieve(AchieveEnum.doing)
                    .user(user)
                    .build();
            scheduleRepository.save(sch);
        }for(int i =0; i < 5; i++){
            User user = userRepository.findByEmail("333@naver.com").get();
            Schedule sch = Schedule.builder()
                    .msg("내용"+i)
                    .achieve(AchieveEnum.doing)
                    .user(user)
                    .build();
            scheduleRepository.save(sch);
        }
    }
    @Test
    void test(){
        User user = userRepository.findByEmail("111@naver.com").get();
        Schedule sch = Schedule.builder()
                .msg("내용1")
                .achieve(AchieveEnum.doing)
                .user(user)
                .build();
        scheduleRepository.save(sch);
        List<Schedule> arr = scheduleRepository.findAllByUser_emailAndCreateDate("111@naver.com", LocalDate.now());
        arr.stream()
                .map(e -> e.getMsg())
                .forEach(System.out::println);

    }
}
