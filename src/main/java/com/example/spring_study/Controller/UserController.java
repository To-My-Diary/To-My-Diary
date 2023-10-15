package com.example.spring_study.Controller;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.LoginDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Service.UserService;
import com.example.spring_study.Util.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    //  WebSecurity 적용 test
    @GetMapping(value = "/user/test")
    @ResponseBody
    public ResponseDto test(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return new ResponseDto(ResponseStatus.SUCCESS);
    }
    @GetMapping(value = "/user/test2")
    @ResponseBody
    public ResponseDto test2(@AuthenticationPrincipal String userEmail){    // Security Session(Security Context Holder에 저장되었는지 email을 매개변수로 받음)
        System.out.println("userEmail : "+userEmail);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return new ResponseDto(ResponseStatus.SUCCESS);
    }
    //  회원가입 요청 - Post
    @PostMapping(value="/user/join")
    @ResponseBody
    public ResponseDto join(@Valid @RequestBody JoinDto joinDto, BindingResult bindingResult){
        List<String> error_list = UserValidation.getValidationError(bindingResult);
        if(!error_list.isEmpty()){
            return new ResponseDto(false, null, HttpStatus.BAD_REQUEST.value(), error_list);
        }

        if (!UserValidation.isRegexEmail(joinDto.getEmail())){  // 이메일 형식 오류
            return new ResponseDto(ResponseStatus.POST_EMAIL_INVALID);
        }else if(!UserValidation.isRegexPw(joinDto.getPw())){   // 비밀번호 형식 오류
            return new ResponseDto(ResponseStatus.POST_PASSSWORD_INVALID);
        }else if (!UserValidation.isRegexTel(joinDto.getTel())){    // 전화번호 형식 오류
            return new ResponseDto(ResponseStatus.POST_TEL_INVALID);
        }else if(!joinDto.getPw().equals(joinDto.getConfirmPw())){  // 비밀번호 확인 시 불일치 오류
            return new ResponseDto(ResponseStatus.POST_PASSWORD_DIFF);
        };

        userService.create(joinDto);

        //  오류가 없다면 생성된 Success return
        return new ResponseDto(ResponseStatus.JOIN_SUCCESS);
    }

    @PostMapping(value = "/user/login")
    @ResponseBody
    public ResponseDto login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult, HttpServletResponse response){
        List<String> error_list = UserValidation.getValidationError(bindingResult);
        if(!error_list.isEmpty()){
            return new ResponseDto(false, null, HttpStatus.BAD_REQUEST.value(), error_list);
        }

        User user = userService.login(loginDto);
        String token = jwtTokenProvider.createToken(loginDto.getEmail(), loginDto.getPw());

        // Front에서 header값으로 받을 수 있도록 구현
        response.setHeader("Authorization", "Bearer "+token);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }
    @GetMapping("/user")
    @ResponseBody
    public ResponseDto getUser(Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        return new ResponseDto(user);
    }

}
