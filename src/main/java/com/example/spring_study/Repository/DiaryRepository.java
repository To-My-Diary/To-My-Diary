package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.AchieveEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUser(User user);

    List<Diary> findAllByAchieve(AchieveEnum achieve); //achieve라는 기분으로 불러온것

    List<Diary> findAllByCreateDate(LocalDate local); //날짜로 불러오기 위해서

}
