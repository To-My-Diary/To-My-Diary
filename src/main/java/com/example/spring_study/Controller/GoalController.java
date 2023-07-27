package com.example.spring_study.Controller;

import com.example.spring_study.DTO.GoalDto;
import com.example.spring_study.Service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GoalController {
    
    private final GoalService goalService;

    /** 목표 저장 */
    @PostMapping("/save/goal")
    public void saveGoal(@RequestBody GoalDto goalDto) {
        goalService.createGoal(goalDto);
    }

//    /** 목표 수정 */
//    @PostMapping("/modify/goal")
//    public void modifyGoal(@RequestBody GoalDto goalDto) {
//        goalService.modifyGoal(goalDto);
//    }
//
//    /** 목표 삭제 */
//    @PostMapping("/delete/goal/{goalId}")
//    public void deleteGoal(@PathVariable("goalId") Long goalId) {
//        goalService.deleteGoal(goalId);
//    }


}
