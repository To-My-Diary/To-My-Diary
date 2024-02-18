package com.example.spring_study.Repository;

import com.example.spring_study.Entity.DetailGoal;
import com.example.spring_study.Enum.AchieveEnum;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailGoalRepository extends JpaRepository<DetailGoal, Long> {
    /**
     * 목표별 상세 목표 가져오기
     */
    List<DetailGoal> findAllByGoal_GoalId(Long id);

    /**
     * 특정 달의 상세 목표 가져오기
     */
    List<DetailGoal> findAllByPlanDateBetweenAndGoal_User_Email(LocalDate start, LocalDate end, String userId);

    /**
     * 사용자별 상세 목표 가져오기
     */
    List<DetailGoal> findAllByGoal_User_Email(String email);

    /**
     * 사용자별 달성별 상세 목표 가져오기
     */
    List<DetailGoal> findAllByGoal_User_EmailAndAchieve(String email, AchieveEnum achieve);

    /**
     * 사용자별 날짜별 상세 목표 가져오기
     */
    List<DetailGoal> findAllByGoal_User_EmailAndCreateDate(String email, LocalDate date);

    /**
     * 사용자별 날짜별 달성별 상세 목표 가져오기
     */
    List<DetailGoal> findAllByGoal_User_EmailAndCreateDateAndAchieve(String email, LocalDate date, AchieveEnum achieve);

    /**
     * 사용자별 날짜별 상세 목표를 가져오되 달성별로 오름차순 정렬
     */
    List<DetailGoal> findAllByGoal_User_EmailAndCreateDateOrderByAchieve(String email, LocalDate date);

    /**
     * 사용자별 날짜별 상세 목표를 가져오되 달성별로 내림차순 정렬
     */
    List<DetailGoal> findAllByGoal_User_EmailAndCreateDateOrderByAchieveDesc(String email, LocalDate date);

    /**
     * 목표 아이디로 목표 상세 전부 삭제하기
     */
    void deleteAllByGoal_GoalId(Long goalId);

    Optional<DetailGoal> findByDetailGoalId(Long detailGoalId);
}
