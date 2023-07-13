package com.example.spring_study.Controller;

import com.example.spring_study.Dto.JoinDto;
import com.example.spring_study.Service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //  Json 객체 생성
    public Object createJSON(String key, String value){
        JSONObject obj = new JSONObject();
        obj.put(key, value);
        return obj.toString();
    }

    //  회원가입 페이지 접근 - Get
    @GetMapping(value = "/join")
    public String join(){
        return "join";
    }

    //  회원가입 요청 - Post
    @PostMapping(value="/doJoin")
    @ResponseBody
    public Object doJoin(@Valid JoinDto joinDto, BindingResult bindingResult){
        JSONArray jArray = new JSONArray();
        //  Spring Validation을 이용하여 오류 메시지 return
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error->{
                jArray.put(createJSON(error.getField(), error.getDefaultMessage()));
            });
            return jArray.toString();
        }
        //  오류가 없다면 생성된 joinDto return
        userService.create(joinDto);
        return joinDto;
    }
}
