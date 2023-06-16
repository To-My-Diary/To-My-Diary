package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.AchieveEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUser(User user);

    List<Diary> findAllByAchieve(AchieveEnum achieve); //달성이라는 변수를 줄 때 

}
