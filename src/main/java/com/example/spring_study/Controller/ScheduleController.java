package com.example.spring_study.Controller;

import com.example.spring_study.DTO.ClickDate;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.DTO.ScheduleDto;
import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Service.ScheduleService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping(value = "/get/schedule")
    public ResponseDto getSchedules(Principal principal) {
        List<Schedule> scheduleList = scheduleService.getSchedule(principal);
        return new ResponseDto(scheduleList);
    }

    @PostMapping(value = "/get/schedule")
    public ResponseDto getUniqueSchedules(Principal principal, @RequestBody ClickDate clickDate) {
        List<Schedule> scheduleList = scheduleService.getUniqueSchedule(principal, clickDate);
        return new ResponseDto(scheduleList);
    }

    /**
     * 할 일 저장
     */
    @PostMapping("/save/schedule")
    public ResponseDto saveSchedule(@RequestBody ScheduleDto scheduleDto, Principal principal) {
        scheduleService.saveSchedule(scheduleDto, principal);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    /**
     * 할 일 수정
     */
    @PostMapping("/modify/schedule")
    public ResponseDto modifySchedule(@RequestBody ScheduleDto scheduleDto, Principal principal) {
        scheduleService.modifySchedule(scheduleDto, principal);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    /**
     * 할 일 삭제
     */
    @PostMapping("/delete/schedule/{scheduleId}")
    public ResponseDto deleteSchedule(@PathVariable("scheduleId") Long scheduleId, Principal principal) {
        scheduleService.deleteSchedule(scheduleId, principal);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

}
