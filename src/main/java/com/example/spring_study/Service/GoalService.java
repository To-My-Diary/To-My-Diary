package com.example.spring_study.Service;

import com.example.spring_study.DTO.DetailGoalDto;
import com.example.spring_study.DTO.GoalDto;
import com.example.spring_study.Entity.DetailGoal;
import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Repository.DetailGoalRepository;
import com.example.spring_study.Repository.GoalRepository;
import com.example.spring_study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final DetailGoalRepository detailGoalRepository;

    /** 목표 & 세부 목표 설정하기 */
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

    /** 한 달 메인 목표 모아 보여주기 */
    public List<Goal> goalsByMonth(int year, int month) {
        List<LocalDate> dates = getStartAndEnd(year, month);
        return goalRepository.findAllByPlanDateBetween(dates.get(0), dates.get(1));
    }

    /** 한 달 상세 목표 모아 보여주기 */
    public List<DetailGoal> detailGoalsByMonth(int year, int month) {
        List<LocalDate> dates = getStartAndEnd(year, month);
        return detailGoalRepository.findAllByPlanDateBetween(dates.get(0), dates.get(1));
    }

    /** 한 달 메인 목표 모아 보여주기 */
    public void getMainGoalsByMonth() {
        // 한 달 기준으로 메인 목표 리스트 가져오기

        // 정렬됐는지 확인하기

    }

    /** 목표 달성 여부 저장 & 수정하기 */
    public void setAchieve() {
        // 달성 여부 저장하기

        // 달성률 수정하기

    }

    /** 목표 상세 페이지 */
    public void showGoalDetail() {
        // 현재일 기준 목표 달성까지 남은 날짜 계산하기

        // 달성률 가져오기

        // 메인 목표 & 상세 목표 리스트 가져오기

    }

    /** 목표 상세 페이지 모아보기 */
    public void showAllGoalDetail() {
        // 모든 목표 상세 페이지 리스트 가져오기

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

}
