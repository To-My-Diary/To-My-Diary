package com.example.spring_study;

import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.GenderEnum;
import com.example.spring_study.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringStudyApplicationTests {
    @Autowired
    UserRepository userRepository;
    @Test
    void test(){
        User user1 = User.builder()
                .name("정우용")
                .email("111@naver.com")
                .gender(GenderEnum.M)
                .pw("12345")
                .build();
        Goal goal = Goal.builder()
                .content("제목")
                .achieve_rate(10)
                .achieve(true)
                .build();
        user1.addGoalList(goal);
        userRepository.save(user1);
    }
}
