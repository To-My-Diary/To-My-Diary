package com.example.spring_study.Service;

import com.example.spring_study.DTO.DetailGoalDto;
import com.example.spring_study.Entity.DetailGoal;
import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Exception.NotFoundDetailGoalException;
import com.example.spring_study.Repository.DetailGoalRepository;
import com.example.spring_study.Repository.GoalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetailGoalService {
    private final DetailGoalRepository detailGoalRepository;
    private final GoalRepository goalRepository;

    @Transactional
    public void create(DetailGoalDto detailGoalDto) {
        Goal goal = goalRepository.findByGoalId(detailGoalDto.getGoalId());
        DetailGoal detailGoal = dtoToEntity(detailGoalDto, goal);
        goal.addDetailGoals(detailGoal);
        detailGoalRepository.save(detailGoal);
    }

    private DetailGoal dtoToEntity(DetailGoalDto detailGoalDto, Goal goal) {
        return DetailGoal.builder()
                .detailGoalId(detailGoalDto.getDetailGoalId())
                .content(detailGoalDto.getContent())
                .achieve(detailGoalDto.getAchieve())
                .planDate(detailGoalDto.getPlanDate())
                .goal(goal)
                .color(goal.getColor())
                .build();
    }

    public List<DetailGoal> findAllByGoalId(Long goalId) {
        return detailGoalRepository.findAllByGoal_GoalId(goalId);
    }

    public void modify(DetailGoalDto detailGoalDto) {
        DetailGoal detailGoal = detailGoalRepository.findByDetailGoalId(detailGoalDto.getDetailGoalId())
                .orElseThrow(() -> new NotFoundDetailGoalException(
                        String.format("Not Found %d DetailGoal", detailGoalDto.getDetailGoalId())));
        detailGoal.setContent(detailGoalDto.getContent());
        detailGoalRepository.save(detailGoal);
    }
}
