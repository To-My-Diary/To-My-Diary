package com.example.spring_study.Controller;

import com.example.spring_study.DTO.CalendarGoalDto;
import com.example.spring_study.DTO.GoalDetailPageDto;
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
    public String saveGoal(@RequestBody GoalDto goalDto) {
        goalService.createGoal(goalDto);
        return "목표 [" + goalDto.getContent() + "]를 저장했습니다.";
    }

    /** 목표 수정 */
    @PostMapping("/goal/modify")
    public String modifyDiary(@RequestBody GoalDto goalDto) {
        goalService.modifyGoal(goalDto);
        return "목표 [" + goalDto.getContent() + "]를 수정했습니다.";
    }

    /** 메인 목표 삭제 */
    @PostMapping("/goal/delete/{goalId}")
    public String deleteGoal(@PathVariable("goalId") Long goalId) {
        goalService.deleteGoal(goalId);
        return "목표 [" + goalId + "]가 삭제 됐습니다.";
    }

    /** 달마다 메인 목표 리스트 가져오기 */
    @GetMapping("/goal/{year}/{month}")
    public List<Goal> goalsByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        return goalService.goalsByMonth(year, month);
    }

    /** 달마다 목표 상세 리스트 가져오기 */
    @GetMapping("/detail/goal/{year}/{month}")
    public List<DetailGoal> detailGoalsByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        return goalService.detailGoalsByMonth(year, month);
    }

    /** 메인 목표 달성 여부 설정 */
    @PostMapping("/goal/achieve")
    public String setGoalAchieve(@RequestBody GoalDto goalDto) {
        goalService.setGoalAchieve(goalDto);
        return "[" + goalDto.getAchieve().name() + "]으로 변경됐습니다.";
    }

    /** 목표 상세 페이지 */
    @GetMapping("/goal/detail/{goalId}")
    public GoalDetailPageDto setGoalAchieve(@PathVariable("goalId") Long goalId) {
        return goalService.getGoalDetailPage(goalId);
    }

    /** 달력에 목표 표시 */
    @GetMapping("/calendar/goal/{year}/{month}")
    public List<CalendarGoalDto> getCalendarGoal(@PathVariable("year") int year, @PathVariable("month") int month) {
        return goalService.getCalendarGoal(year, month);
    }

}
