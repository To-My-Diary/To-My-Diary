package com.example.spring_study.Controller;

import com.example.spring_study.DTO.ScheduleDto;
import com.example.spring_study.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(originPatterns = "http://localhost:3000")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /** 할 일 저장 */
    @PostMapping("/save/schedule")
    public void saveDiary(@RequestBody ScheduleDto scheduleDto) {
        SecurityContext context = SecurityContextHolder.getContext();

        System.out.print(context);

        scheduleService.saveSchedule(scheduleDto);
    }
    
    /** 할 일 수정 */
    @PostMapping("/modify/schedule")
    public void modifyDiary(@RequestBody ScheduleDto scheduleDto) {
        scheduleService.modifySchedule(scheduleDto);
    }

    /** 할 일 삭제 */
    @PostMapping("/delete/schedule/{scheduleId}")
    public void deleteDiary(@PathVariable("scheduleId") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }
    
}
