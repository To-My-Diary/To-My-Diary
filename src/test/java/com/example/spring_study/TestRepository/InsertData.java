package com.example.spring_study.TestRepository;

import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.EmotionEnum;
import com.example.spring_study.Enum.GenderEnum;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Repository.DiaryRepository;
import com.example.spring_study.Repository.UserRepository;
import com.example.spring_study.Security.CustomAuthenticationProvider;
import com.example.spring_study.Security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class InsertData {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void jwttest(){
        String token="eyJyZWdEYXRlIjoxNjkyNzYyMjQ3MzU3LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2VtYWlsIjoiMTExQG5hdmVyLmNvbSIsInBhc3N3b3JkIjoicXdlcjEyMzQhIiwiZXhwIjoxNjkyNzkxMDQ3fQ.Y18GWIFIYr-HfVBm3wRt0ibTd8DSA89vQzYQSKAZnNs";
        System.out.println(rs);

        System.out.println(jwtTokenProvider.getUserEmail(token));

    }
    @Test
    public void logintest(){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("111@naver.com", "111");
        Authentication authentication = customAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        UserDetails user = (UserDetails) authentication.getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getName());
    }
    @Test
    public void insertUser() {
        User user = User.builder()
                .email("topjoy22@naver.com")
                .gender(GenderEnum.F)
                .name("조유진")
                .pw("password")
                .tel("01037902800")
                .build();
        userRepository.save(user);
    }

    @Test
    public void insertDiary() {
        User user = userRepository.findByEmail("topjoy22@naver.com").get();
        int random = (int)((Math.random()*10000)%10);
        Diary diary = Diary.builder()
                .subject("일기 제목 " + random)
                .content("일기 내용 " + random)
                .emotion(EmotionEnum.happy)
                .user(user)
                .build();
        diaryRepository.save(diary);
    }

}
