package com.example.spring_study;

import com.example.spring_study.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringStudyApplicationTests {
    @Autowired
    UserRepository userRepository;

    void test1(){
        User user = User.builder()
                .name("ggg")
                .age(123)
                .build();
        userRepository.save(user);

    }
}
