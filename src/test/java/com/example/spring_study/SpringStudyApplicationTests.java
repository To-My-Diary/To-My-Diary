package com.example.spring_study;

import com.example.spring_study.Enum.GenderType;
import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Entity.User;
<<<<<<< Updated upstream
=======
import com.example.spring_study.Enum.AchieveEnum;
import com.example.spring_study.Enum.GenderEnum;
>>>>>>> Stashed changes
import com.example.spring_study.Repository.UserRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class JwyTests {
    @Autowired
    UserRepository userRepository;
    @Test
    void test(){
        User user1 = User.builder()
                .name("정우용")
                .email("111@naver.com")
                .gender(GenderType.M)
                .pw("12345")
                .pw_val("12345")
                .build();
        Goal goal = Goal.builder()
                .content("제목")
                .achieve_rate(10)
                .achieve(AchieveEnum.doing)
                .build();
        user1.addGoalList(goal);
        userRepository.save(user1);
    }
    @Test
    void test1(){
        User user = userRepository.findByEmailAndPw("111@naver.com", "12345").get();

        Assertions.assertThat("12345").isEqualTo(user.getPw());

        List<User> arr = userRepository.findByGender(GenderEnum.F);

        arr.stream()
                .map(e -> e.getName())
                .forEach(System.out::println);
    }
}
