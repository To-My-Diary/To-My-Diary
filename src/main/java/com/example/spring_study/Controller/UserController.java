package com.example.spring_study.Controller;

import com.example.spring_study.Dto.JoinDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/join")
    public String join(){
        return "login";
    }


    @PostMapping(value="/doJoin")
    @ResponseBody
    public Object doJoin(@RequestBody JoinDto joinDto){
        System.out.println(joinDto.getEmail());
        System.out.println(joinDto.getPw());
        System.out.println(joinDto.getName());
        System.out.println(joinDto.getTel());
        System.out.println(joinDto.getGender());
        userService.create(joinDto);
        return String.valueOf(1111);
    }
}
