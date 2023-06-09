package com.example.spring_study;

import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.AchieveEnum;
import com.example.spring_study.Enum.GenderEnum;
import com.example.spring_study.Repository.GoalRepository;
import com.example.spring_study.Repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JwyTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GoalRepository goalRepository;
    @Test
    void createData(){
        User user1 = User.builder()
                .name("홍길동")
                .email("111@naver.com")
                .gender(GenderEnum.M)
                .pw("12345")
                .tel("010-1111-1111")
                .build();
        userRepository.save(user1);
        User user2 = User.builder()
                .name("홍길순")
                .email("222@naver.com")
                .gender(GenderEnum.F)
                .pw("0987")
                .tel("010-2222-2222")
                .build();
        userRepository.save(user2);
        User user3 = User.builder()
                .name("임꺽정")
                .email("333@naver.com")
                .gender(GenderEnum.M)
                .pw("4567")
                .tel("010-3333-3333")
                .build();
        userRepository.save(user3);
    }
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
                .achieve(AchieveEnum.doing)
                .build();
        user1.addGoalList(goal);
        userRepository.save(user1);
    }
    @Test
    void test1(){
        User user = userRepository.findByEmailAndPw("111@naver.com", "12345").get();

        Assertions.assertThat("12345").isEqualTo(user.getPw());

        List<User> arr = userRepository.findByGender(GenderEnum.M);

        arr.stream()
                .map(e -> e.getName())
                .forEach(System.out::println);
    }
    @Test
    void test3(){
        Goal goal1 = Goal.builder()
                .content("제목222")
                .achieve_rate(20)
                .achieve(AchieveEnum.fail)
                .build();
        goalRepository.save(goal1);
        List<Goal> arr = goalRepository.findAllByOrderByCreateDateDesc();
        arr.stream()
                .map(e -> e.getGoalId())
                .forEach(System.out::println);
        List<Goal> arr1 = goalRepository.findAllByOrderByCreateDateAsc();
        arr1.stream()
                .map(e -> e.getGoalId())
                .forEach(System.out::println);
    }
    @Test
    void test4(){
        List<Goal> arr = goalRepository.findByAchieve(AchieveEnum.achieve);
        arr.stream()
                .map(e -> e.getAchieve())
                .forEach(System.out::println);
    }
    @Test
    void test5(){
        User user = userRepository.findByEmail("111@naver.com").get();
        List<Goal> arr = goalRepository.findByUserEmail(user.getEmail());
        arr.stream()
                .map(e -> e.getAchieve())
                .forEach(System.out::println);
    }
}
