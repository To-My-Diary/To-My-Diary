package com.example.spring_study.Controller;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.Exception.SignUpEmailException;
import com.example.spring_study.Exception.SignUpTelException;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    //  회원가입 요청 - Post
    @PostMapping(value="/user")
    @ResponseBody
    public ResponseDto doJoin(@Valid @RequestBody JoinDto joinDto, BindingResult bindingResult){
        List<String> error_list = new ArrayList<>();
        //  Spring Validation을 이용하여 오류 메시지 return
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error->{
                error_list.add(error.getDefaultMessage());
            });

            return new ResponseDto(false, null, HttpStatus.BAD_REQUEST.value(),error_list);
        }else if(!joinDto.getPw().equals(joinDto.getConfirmPw())){
            return new ResponseDto(ResponseStatus.POST_PASSWORD_DIFF);
        }
        //  오류가 없다면 생성된 joinDto return
        try{
            userService.create(joinDto);
        }catch(SignUpEmailException e){
            return new ResponseDto(ResponseStatus.POST_EMAIL_DUPLICATE);
        }catch(SignUpTelException e){
            return new ResponseDto(ResponseStatus.POST_TEL_DUPLICATE);
        }
        return new ResponseDto(ResponseStatus.JOIN_SUCCESS);
    }

}
