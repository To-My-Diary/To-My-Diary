package com.example.spring_study.Controller;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.Exception.SignUpEmailException;
import com.example.spring_study.Exception.SignUpTelException;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Service.UserService;
import com.example.spring_study.Util.RegexValidation;
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
    public ResponseDto join(@Valid @RequestBody JoinDto joinDto, BindingResult bindingResult){
        List<String> error_list = new ArrayList<>();

        if(bindingResult.hasErrors()){  // Spring Validation을 이용하여 null, empty, 공백 검사
            bindingResult.getFieldErrors().forEach(error->{
                error_list.add(error.getDefaultMessage());
            });
            return new ResponseDto(false, null, HttpStatus.BAD_REQUEST.value(),error_list);
        }

        if (!RegexValidation.isRegexEmail(joinDto.getEmail())){  // 이메일 형식 오류
            return new ResponseDto(ResponseStatus.POST_EMAIL_INVALID);
        }else if(!RegexValidation.isRegexPw(joinDto.getPw())){   // 비밀번호 형식 오류
            return new ResponseDto(ResponseStatus.POST_PASSSWORD_INVALID);
        }else if (!RegexValidation.isRegexTel(joinDto.getTel())){    // 전화번호 형식 오류
            return new ResponseDto(ResponseStatus.POST_TEL_INVALID);
        }else if(!joinDto.getPw().equals(joinDto.getConfirmPw())){  // 비밀번호 확인 시 불일치 오류
            return new ResponseDto(ResponseStatus.POST_PASSWORD_DIFF);
        };

        try{
            userService.create(joinDto);
        }catch(SignUpEmailException e){
            return new ResponseDto(ResponseStatus.POST_EMAIL_DUPLICATE);
        }catch(SignUpTelException e){
            return new ResponseDto(ResponseStatus.POST_TEL_DUPLICATE);
        }

        //  오류가 없다면 생성된 Success return
        return new ResponseDto(ResponseStatus.JOIN_SUCCESS);
    }

}
