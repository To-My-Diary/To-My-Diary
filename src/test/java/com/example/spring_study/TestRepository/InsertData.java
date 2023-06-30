package com.example.spring_study.TestRepository;

import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.EmotionEnum;
import com.example.spring_study.Enum.GenderEnum;
import com.example.spring_study.Repository.DiaryRepository;
import com.example.spring_study.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InsertData {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiaryRepository diaryRepository;

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
