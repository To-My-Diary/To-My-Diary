package com.example.spring_study.Controller;

import com.example.spring_study.DTO.ClickDate;
import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Home {
    private final ScheduleService scheduleService;
    @GetMapping(value = "/index")
    public String index(Model model){
        List<Schedule> scheduleList=  scheduleService.getSchedule();
        model.addAttribute("scheduleList", scheduleList);
        return "index";
    }
    @PostMapping(value = "/index")
    @ResponseBody
    public Object index(@RequestBody ClickDate clickDate){
        System.out.println(String.format("%s년%s월%s일", clickDate.getYear(), clickDate.getMonth(),clickDate.getDay()));
        List<Schedule> scheduleList=  scheduleService.getSchedule(clickDate);
        scheduleList.stream()
                .map(e -> e.getCreateDate())
                .forEach(System.out::println);
        return scheduleList;
    }
}
