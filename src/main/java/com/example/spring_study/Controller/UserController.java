package com.example.spring_study.Controller;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.LoginDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Exception.IncorrectPasswordException;
import com.example.spring_study.Exception.NotFoundUserException;
import com.example.spring_study.Exception.SignUpEmailException;
import com.example.spring_study.Exception.SignUpTelException;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Service.UserService;
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

    private final JwtTokenProvider jwtTokenProvider;

    //  Json 객체 생성
    public Object createJSON(String key, Object value){
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
            return createJSON("jArray", jArray);
        }else if(!joinDto.getPw().equals(joinDto.getConfirmPw())){
            return createJSON("msg", "2개의 패스워드가 일치하지 않습니다.");
        }
        //  오류가 없다면 생성된 joinDto return
        try{
            userService.create(joinDto);
        }catch(SignUpEmailException e){
            return createJSON("msg", e.getMessage());
        }catch(SignUpTelException e){
            return createJSON("msg", e.getMessage());
        }
        return joinDto;
    }

    // 로그인 페이지 이동 메소드
    @GetMapping(value="/loginForm")
    public String login(){ return "loginForm";}


    // 로그인 실행 메소드
    @PostMapping(value = "/login")
    @ResponseBody
    public Object doLogin(LoginDto loginDto){
        User user = null;
        try{
            user = userService.login(loginDto);
        }catch(NotFoundUserException e){
            return createJSON("msg", e.getMessage());
        }catch(IncorrectPasswordException e){
            return createJSON("msg", e.getMessage());
        }

        String jwtToken = jwtTokenProvider.createToken(user.getEmail());

        return jwtToken;
    }
}
