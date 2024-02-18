package com.example.spring_study.Controller;

import com.example.spring_study.DTO.GoalDetailPageDto;
import com.example.spring_study.DTO.GoalDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.Entity.DetailGoal;
import com.example.spring_study.Entity.Goal;
import com.example.spring_study.Service.GoalService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GoalController {

    private final GoalService goalService;

    /**
     * 목표 저장
     */
    @PostMapping("/save/goal")
    public ResponseDto saveGoal(@RequestBody GoalDto goalDto) {
        goalService.createGoal(goalDto);
        return new ResponseDto<>(ResponseStatus.SUCCESS);
    }

//    @PostMapping("save/detail/goal")
//    public ResponseDto saveDetailGoal(@RequestBody DetailGoalDto detailGoalDto) {
//        goalService.createDetailGoal(detailGoalDto);
//        return new ResponseDto<>(ResponseStatus.SUCCESS);
//    }

    /**
     * 목표 수정
     */
    @PostMapping("/goal/modify")
    public ResponseDto modifyDiary(@RequestBody GoalDto goalDto) {
        goalService.modifyGoal(goalDto);
        return new ResponseDto<>(ResponseStatus.SUCCESS);
    }

    /**
     * 메인 목표 삭제
     */
    @PostMapping("/goal/delete/{goalId}")
    public ResponseDto deleteGoal(@PathVariable("goalId") Long goalId) {
        goalService.deleteGoal(goalId);
        return new ResponseDto<>(ResponseStatus.SUCCESS);
    }

    /**
     * 달마다 메인 목표 리스트 가져오기
     */
    @GetMapping("/goal/{year}/{month}")
    public List<Goal> goalsByMonth(@PathVariable("year") int year, @PathVariable("month") int month,
                                   Principal principal) {
        return goalService.goalsByMonth(year, month, principal.getName());
    }

    /**
     * 달마다 목표 상세 리스트 가져오기
     */
    @GetMapping("/detail/goal/{year}/{month}")
    public List<DetailGoal> detailGoalsByMonth(@PathVariable("year") int year, @PathVariable("month") int month,
                                               Principal principal) {
        return goalService.detailGoalsByMonth(year, month, principal.getName());
    }

    /**
     * 메인 목표 달성 여부 설정
     */
    @PostMapping("/goal/achieve")
    public String setGoalAchieve(@RequestBody GoalDto goalDto) {
        goalService.setGoalAchieve(goalDto);
        return "[" + goalDto.getAchieve().name() + "]으로 변경됐습니다.";
    }

    /**
     * 목표 상세 페이지
     */
    @GetMapping("/goal/detail/{goalId}")
    public GoalDetailPageDto detailGoalByPage(@PathVariable("goalId") Long goalId) {
        return goalService.getGoalDetailPage(goalId);
    }

    /**
     * 달력에 목표 표시
     */
    @GetMapping("/calendar/goal/{year}/{month}")
    public ResponseDto getCalendarGoal(@PathVariable("year") int year, @PathVariable("month") int month,
                                       Principal principal) {
//        int y = Integer.parseInt(year);
//        int m = Integer.parseInt(month);
        return new ResponseDto(goalService.getCalendarGoal(year, month, principal.getName()));
    }

    /** 목표 상세 페이지 모아보기 */
    /**
     * 로그인 구현이 완성되면 쿠키로 사용자 이메일을 받도록 수정해야 함
     */
    @GetMapping("/goal/detail/all")
    public List<GoalDetailPageDto> detailGoalsByPage(String email) {
        return goalService.getAllGoalDetail("topjoy22@naver.com");
    }

}
