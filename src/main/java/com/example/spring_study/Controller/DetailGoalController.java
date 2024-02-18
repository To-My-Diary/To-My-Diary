package com.example.spring_study.Controller;

import com.example.spring_study.DTO.DetailGoalDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.Service.DetailGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DetailGoalController {
    private final DetailGoalService detailGoalService;

    @PostMapping("/save/detailgoal")
    @ResponseBody
    public ResponseDto createDetailGoal(@RequestBody DetailGoalDto detailGoalDto) {
        detailGoalService.create(detailGoalDto);
        return new ResponseDto<>(ResponseStatus.SUCCESS);
    }

    @GetMapping("/select/detailgoal")
    @ResponseBody
    public ResponseDto selectDetailGoal(@RequestBody DetailGoalDto detailGoalDto) {
        return new ResponseDto(detailGoalService.findAllByGoalId(detailGoalDto.getGoalId()));
    }

    @PostMapping("/modify/detailgoal")
    @ResponseBody
    public ResponseDto modifyDetailGoal(@RequestBody DetailGoalDto detailGoalDto) {
        detailGoalService.modify(detailGoalDto);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }
}
