package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.AchieveEnum;
import com.example.spring_study.Enum.EmotionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    /** 기본키로 일기 찾기 */
    Diary findByDiaryId(Long id);

    List<Diary> findByUser(User user);

    List<Diary> findAllByEmotion(EmotionEnum emotion); //emotion이라는 기분으로 불러온것

    List<Diary> findAllByCreateDate(LocalDate local); //날짜로 불러오기 위해서

}
