package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Enum.AchieveEnum;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAll();  // 모든 Gaol 조회 (생략 가능)

    List<Goal> findAllByUser_Email(String email); // 사용자 이메일로 전체 목표 조회

    List<Goal> findAllByOrderByCreateDateDesc();  // 목표 날짜(내림차순) 조희

    List<Goal> findAllByOrderByCreateDateAsc();   // 목표 날짜(내림차순) 조희

    List<Goal> findByAchieve(AchieveEnum Type);   // 목표 달성여부 값으로 조회

    List<Goal> findByUserEmail(String email);

    /**
     * 목표 아이디로 가져오기
     */
    Goal findByGoalId(Long goalId);

    /**
     * 특정 달의 목표 가져오기
     */
    List<Goal> findAllByPlanDateBetweenAndUser_Email(LocalDate start, LocalDate end, String userId);
}