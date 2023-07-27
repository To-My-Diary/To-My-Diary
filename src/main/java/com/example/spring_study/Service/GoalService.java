package com.example.spring_study.Service;

import com.example.spring_study.DTO.DetailGoalDto;
import com.example.spring_study.DTO.GoalDto;
import com.example.spring_study.Entity.DetailGoal;
import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Repository.DetailGoalRepository;
import com.example.spring_study.Repository.GoalRepository;
import com.example.spring_study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final DetailGoalRepository detailGoalRepository;

    /** 목표 & 세부 목표 설정하기 */
    public void createGoal(GoalDto goalDto) {
        Goal goal = dtoToEntity(goalDto);
        goalRepository.save(goal);
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

    /** DetailGoalDto -> DetailGoal Entity */
    DetailGoal dtoToEntity(DetailGoalDto dto){
        DetailGoal detailGoal = DetailGoal.builder()
//                .detailGoalId(dto.getDetailGoalId())
//                .goal(goalRepository.findById(goalId).get())
                .content(dto.getContent())
                .achieve(dto.getAchieve())
                .planDate(dto.getPlanDate()).build();
        return detailGoal;
    }

    /** GoalDto -> Goal Entity */
    Goal dtoToEntity(GoalDto dto) {
        // dtoList -> entityList
        List<DetailGoal> detailGoalList = new ArrayList<>();
        for (DetailGoalDto detailGoalDto : dto.getDetailGoals()) {
            detailGoalList.add(dtoToEntity(detailGoalDto));
        }

        Goal goal = Goal.builder()
//                .goalId(dto.getGoalId())
                .content(dto.getContent())
                .achieve(dto.getAchieve())
                .achieveRate(dto.getAchieveRate())
                .color(dto.getColor())
                .planDate(dto.getPlanDate())
                .user(userRepository.findByEmail(dto.getUserId()).get())
                .detailGoals(detailGoalList)
                .build();
        return goal;
    }

}
