package com.example.spring_study.Controller;

import com.example.spring_study.DTO.GoalDto;
import com.example.spring_study.Entity.DetailGoal;
import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GoalController {
    
    private final GoalService goalService;

    /** 목표 저장 */
    @PostMapping("/save/goal")
    public void saveGoal(@RequestBody GoalDto goalDto) {
        goalService.createGoal(goalDto);
    }


    @GetMapping("/goal/{year}/{month}")
    public List<Goal> goalsByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        return goalService.goalsByMonth(year, month);
    }


    @GetMapping("/detail/goal/{year}/{month}")
    public List<DetailGoal> detailGoalsByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        return goalService.detailGoalsByMonth(year, month);
    }


}
