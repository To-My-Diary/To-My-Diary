package com.example.spring_study.Controller;

import com.example.spring_study.Repository.ScheduleRepository;
import com.example.spring_study.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class Home {
    private final ScheduleService scheduleService;
}
