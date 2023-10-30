package com.example.spring_study.Controller;

import com.example.spring_study.DTO.ScheduleDto;
import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Exception.NotFoundScheduleException;
import com.example.spring_study.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @ResponseBody 어노테이션 삭제 후 @RestController 어노테이션 추가
@RequiredArgsConstructor
public class Home {
    private final ScheduleService scheduleService;

    public Object createMsgJSON(String msg) {
        JSONObject obj = new JSONObject();
        obj.put("msg", msg);
        return obj.toString();
    }

    @PostMapping(value = "/index/modify_scheduleAchieve")
    public Object modifyScheduleAchieve(@RequestBody ScheduleDto scheduleDto) {
        Schedule schedule = null;
        try {
            schedule = scheduleService.modifyAchieve(scheduleDto);
        } catch (NotFoundScheduleException e) {
            return createMsgJSON(String.format("Not Found %d Schedule", scheduleDto.getScheduleId()));
        }
        return schedule;
    }
}
