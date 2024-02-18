package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.EmotionEnum;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    /**
     * 기본키로 일기 찾기
     */
    Diary findByDiaryId(Long id);

    List<Diary> findAllByUser_Email(String userId);

    List<Diary> findAllByEmotion(EmotionEnum emotion); //emotion이라는 기분으로 불러온것

    List<Diary> findByUser(User user);

    List<Diary> findAllByCreateDate(LocalDate local); // 날짜로 불러오기 위해서

    @Query("select d from Diary d join fetch d.user where d.user.email = :userEmail and d.createDate = :date")
    List<Diary> findAllByCreateDateAndUser_email(@Param("date") LocalDate date, @Param("userEmail") String userEmail);
}
