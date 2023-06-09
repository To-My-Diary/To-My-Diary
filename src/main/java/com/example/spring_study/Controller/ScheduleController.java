package com.example.spring_study.Controller;

import com.example.spring_study.Dto.ScheduleDto;
import com.example.spring_study.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    /** 할 일 저장 */
    @PostMapping("/save/schedule")
    public void saveDiary(@RequestBody ScheduleDto scheduleDto) {
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
