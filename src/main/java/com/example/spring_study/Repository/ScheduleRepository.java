package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Enum.AchieveEnum;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /**
     * 스케줄 아이디로 찾기
     */
    Optional<Schedule> findByScheduleId(Long id);

    //  특정 사용자 특정 스케줄 가져오기
    Optional<Schedule> findByUser_emailAndScheduleId(String email, Long id);

    /**
     * 사용자별 전체 스케줄 가져오기
     */
    List<Schedule> findAllByUser_email(String email);

    /**
     * 사용자별 달성별 스케줄 가져오기
     */
    List<Schedule> findAllByUser_emailAndAchieve(String email, AchieveEnum achieve);

    /**
     * 사용자별 날짜별 스케줄 가져오기
     */
    List<Schedule> findAllByUser_emailAndCreateDate(String email, LocalDate date);

    /**
     * 사용자별 날짜별 달성별 스케줄 가져오기
     */
    List<Schedule> findAllByUser_emailAndCreateDateAndAchieve(String email, LocalDate date, AchieveEnum achieve);

    /**
     * 사용자별 날짜별 스케줄을 가져오되 달성별로 오름차순 정렬
     */
    List<Schedule> findAllByUser_emailAndCreateDateOrderByAchieve(String email, LocalDate date);

    /**
     * 사용자별 날짜별 스케줄을 가져오되 달성별로 내림차순 정렬
     */
    List<Schedule> findAllByUser_emailAndCreateDateOrderByAchieveDesc(String email, LocalDate date);

    @Query(value = "select s from Schedule as s where user_email =:email " +
            "order by FIELD(achieve ,'achieve') desc, create_date")
    List<Schedule> getScheduleOrderByAchieve(String email);

    @Query(value = "select s from Schedule as s where user_email =:email and create_date=:date " +
            "order by FIELD(achieve ,'achieve') desc, create_date")
    List<Schedule> getClickDateSchedules(String email, LocalDate date);
}
