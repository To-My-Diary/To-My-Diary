package com.example.spring_study.Service;

import com.example.spring_study.DTO.CalendarGoalDto;
import com.example.spring_study.DTO.DetailGoalDto;
import com.example.spring_study.DTO.GoalDetailPageDto;
import com.example.spring_study.DTO.GoalDto;
import com.example.spring_study.Entity.DetailGoal;
import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Repository.DetailGoalRepository;
import com.example.spring_study.Repository.GoalRepository;
import com.example.spring_study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final DetailGoalRepository detailGoalRepository;

    /** 목표 & 세부 목표 저장하기 */
    public void createGoal(GoalDto goalDto) {
        // 목표 저장
        Goal goal = dtoToEntity(goalDto);
        long goalId = goalRepository.save(goal).getGoalId();
        // 세부 목표 저장
        List<DetailGoal> detailGoals = dtoToEntity(goalDto.getDetailGoals(), goalDto.getColor());
        for (DetailGoal detailGoal : detailGoals) {
            // 외래키 설정
            detailGoal.setGoal(goalRepository.findById(goalId).get());
            detailGoalRepository.save(detailGoal);
        }
    }

    /** 목표 & 세부 목표 수정하기 */
    @Transactional
    public void modifyGoal(GoalDto goalDto) {
        // 목표 & 목표 상세 삭제
        deleteGoal(goalDto.getGoalId());
        // 목표 저장
        createGoal(goalDto);
    }

    /** 한 달 메인 목표 모아 보여주기 */
    public List<Goal> goalsByMonth(int year, int month, String userId) {
        List<LocalDate> dates = getStartAndEnd(year, month);
        return goalRepository.findAllByPlanDateBetweenAndUser_Email(dates.get(0), dates.get(1), userId);
    }

    /** 한 달 상세 목표 모아 보여주기 */
    public List<DetailGoal> detailGoalsByMonth(int year, int month, String userId) {
        List<LocalDate> dates = getStartAndEnd(year, month);
        return detailGoalRepository.findAllByPlanDateBetweenAndGoal_User_Email(dates.get(0), dates.get(1), userId);
    }

    /** 메인 목표 달성 여부 설정 */
    @Transactional
    public void setGoalAchieve(GoalDto goalDto) {
        Goal goal = goalRepository.findById(goalDto.getGoalId()).get();
        goal.update(goalDto.getAchieve());
    }

    /** 목표 상세 페이지 */
    public GoalDetailPageDto getGoalDetailPage(Long goalId) {
        // 남은 날짜 계산하기
        Goal goal = goalRepository.findByGoalId(goalId);
        int remainingDays = getRemainingDays(goal);
        // 달성률 가져오기
        int achievement = 0;
        // Dto에 담기
        GoalDetailPageDto dto = new GoalDetailPageDto(remainingDays, achievement, goal);
        return dto;
    }

    /** 목표 & 목표 상세 모두 삭제하기 */
    @Transactional // delete 메소드에 필요한 어노테이션
    public void deleteGoal(Long goalId) {
        detailGoalRepository.deleteAllByGoal_GoalId(goalId);
        goalRepository.deleteById(goalId);
    }

    /** 달력에 목표 표시하기 */
    public List<CalendarGoalDto> getCalendarGoal(int year, int month, String userId) {
        // 한 달 메인 목표 가져오기
        List<Goal> goals = goalsByMonth(year, month, userId);
        // 한 달 목표 상세 가져오기
        List<DetailGoal> detailGoals = detailGoalsByMonth(year, month, userId);
        List<CalendarGoalDto> days = new ArrayList<>();

        // 색깔과 날짜 담기 - 메인 목표
        for (Goal goal : goals) {
            CalendarGoalDto dto = new CalendarGoalDto(Boolean.TRUE, goal.getColor(), goal.getPlanDate().getDayOfMonth());
            days.add(dto);
        }
        // 색깔과 날짜 담기 - 목표 상세
        for (DetailGoal detailGoal : detailGoals) {
            CalendarGoalDto dto = new CalendarGoalDto(Boolean.FALSE, detailGoal.getColor(), detailGoal.getPlanDate().getDayOfMonth());
            days.add(dto);
        }
        return days;
    }

    /** 목표 상세 페이지 모아보기 */
    public List<GoalDetailPageDto> getAllGoalDetail(String email) {
        List<Goal> allByUser_email = goalRepository.findAllByUser_Email(email);
        List<GoalDetailPageDto> dtos = new ArrayList<>();
        for (Goal goal : allByUser_email) {
            dtos.add(getGoalDetailPage(goal.getGoalId()));
        }
        return dtos;
    }

    /** DetailGoalDto List -> DetailGoal Entity List */
    List<DetailGoal> dtoToEntity(List<DetailGoalDto> dto, String color){
        // dtoList -> entityList
        List<DetailGoal> detailGoalList = new ArrayList<>();
        for (DetailGoalDto detailGoalDto : dto) {
            DetailGoal detailGoal = DetailGoal.builder()
//                .detailGoalId(dto.getDetailGoalId())
//                .goal(goalRepository.findById(goalId).get())
                    .content(detailGoalDto.getContent())
                    .achieve(detailGoalDto.getAchieve())
                    .planDate(detailGoalDto.getPlanDate())
                    .color(color).build();
            detailGoalList.add(detailGoal);
        }
        return detailGoalList;
    }

    /** GoalDto -> Goal Entity */
    Goal dtoToEntity(GoalDto dto) {
        Goal goal = Goal.builder()
//                .goalId(dto.getGoalId())
                .content(dto.getContent())
                .achieve(dto.getAchieve())
                .achieveRate(dto.getAchieveRate())
                .color(dto.getColor())
                .planDate(dto.getPlanDate())
                .user(userRepository.findByEmail(dto.getUserId()).get())
//                .detailGoals(detailGoalList)
                .build();
        return goal;
    }

    /** Goal Entity -> GoalDto */
    GoalDto entityToDto(Goal goal) {
        GoalDto dto = new GoalDto(goal.getGoalId(), goal.getContent(), goal.getAchieve(), goal.getAchieveRate()
                , goal.getPlanDate(), goal.getColor(), goal.getUser().getEmail(), null);
        return dto;
    }

    /** DetailGoal Entity -> DetailGoalDto */
    DetailGoalDto entityToDto(DetailGoal detailGoal) {
        DetailGoalDto dto = new DetailGoalDto(detailGoal.getDetailGoalId(), detailGoal.getDetailGoalId()
                , detailGoal.getContent(), detailGoal.getAchieve(), detailGoal.getPlanDate());
        return dto;
    }

    /** 시작 날짜와 마지막 날짜 계산하기 */
    List<LocalDate> getStartAndEnd(int year, int month) {
        // 한 달의 마지막 날짜를 구하기 위한 Calendar 라이브러리
        Calendar cal = Calendar.getInstance();
        cal.set(year,month-1,1);

        // 시작 날짜와 마지막 날짜
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        ArrayList<LocalDate> dates = new ArrayList<>();
        dates.add(startDate); dates.add(endDate);
        return dates;
    }

    private int getRemainingDays(Goal goal) {
        LocalDate now = LocalDate.now();
        LocalDate end = goal.getPlanDate();

        Calendar startCalendar = new GregorianCalendar(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        Date startDT = startCalendar.getTime();
        Calendar endCalendar = new GregorianCalendar(end.getYear(), end.getMonthValue(), end.getDayOfMonth());
        Date endDT = endCalendar.getTime();

        long differenceInMillis = endDT.getTime() - startDT.getTime();
        long years = (differenceInMillis / (365 * 24 * 60 * 60 * 1000L));
        long days = (differenceInMillis / (24 * 60 * 60 * 1000L));

        System.out.printf("\n두 날짜 사이: %d년 %d일", years, days);
        System.out.printf("\n두 날짜 사이: %d일", days + years*365);

        return (int) (days+years*365);
    }
}
