package com.example.spring_study.Controller;

import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Repository.ScheduleRepository;
import com.example.spring_study.Service.ScheduleService;
import com.example.spring_study.VO.ClickDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Home {
    private final ScheduleService scheduleService;
    @GetMapping(value = {"/","/index"})
    public String index(Model model){
        List<Schedule> scheduleList=  scheduleService.getTodaySchedule();
        model.addAttribute("scheduleList", scheduleList);
        return "index";
    }
    @PostMapping(value = {"/","/index"})
    public String index(@RequestBody ClickDate clickDate, Model model){
        System.out.println(String.format("%s년%s월%s일", clickDate.getYear(), clickDate.getMonth(),clickDate.getDay()));
        List<Schedule> scheduleList=  scheduleService.getTodaySchedule();
        model.addAttribute("scheduleList", scheduleList);
        return "index";
    }
}
