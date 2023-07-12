package com.example.spring_study.Controller;

import com.example.spring_study.Dto.JoinDto;
import com.example.spring_study.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public Object createJSON(String msg){
        JSONObject obj = new JSONObject();
        obj.put("msg", msg);
        return obj.toString();
    }
    //  회원가입 페이지 접근 - Get
    @GetMapping(value = "/join")
    @ResponseBody
    public String join(){
        return "join";
    }

    //  회원가입 요청 - Post
    @PostMapping(value="/doJoin")
    @ResponseBody
    public Object doJoin(@Valid JoinDto joinDto, BindingResult bindingResult){
        userService.create(joinDto);
        return String.valueOf(1111);
    }
}
