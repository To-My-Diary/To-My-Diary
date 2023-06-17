package com.example.spring_study.Controller;

import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Repository.ScheduleRepository;
import com.example.spring_study.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
