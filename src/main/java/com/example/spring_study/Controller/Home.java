package com.example.spring_study.Controller;

import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Service.ScheduleService;
import com.example.spring_study.DTO.ClickDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // @ResponseBody 어노테이션 삭제 후 @RestController 어노테이션 추가
@RequiredArgsConstructor
public class Home {

    private final ScheduleService scheduleService;

    // 메인화면 (GET) - request : 없음
    @GetMapping(value = "/index")
    public Object index(){
        List<Schedule> scheduleList=  scheduleService.getSchedule();
        return scheduleList;
    }

    // 메인화면 (POST) - request : ClickDate 필드
    @PostMapping(value = "/index")
    public Object index(@RequestBody ClickDate clickDate){
        List<Schedule> scheduleList=  scheduleService.getSchedule(clickDate);
        return scheduleList;
    }
}
