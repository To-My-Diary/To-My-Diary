package com.example.spring_study.Controller;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.LoginDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Service.UserService;
import com.example.spring_study.Util.UserValidation;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    //  회원가입 요청 - Post
    @PostMapping(value = "/user/join")
    @ResponseBody
    public ResponseDto join(@Valid @RequestBody JoinDto joinDto, BindingResult bindingResult) {
        List<String> error_list = UserValidation.getValidationError(bindingResult);
        if (!error_list.isEmpty()) {
            return new ResponseDto(false, null, HttpStatus.BAD_REQUEST.value(), error_list);
        }
        UserValidation.joinValidation(joinDto);
        return userService.create(joinDto);
    }

    // 로그인
    @PostMapping(value = "/user/login")
    @ResponseBody
    public ResponseDto login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult,
                             HttpServletResponse response) {
        List<String> error_list = UserValidation.getValidationError(bindingResult);
        if (!error_list.isEmpty()) {
            return new ResponseDto(false, null, HttpStatus.BAD_REQUEST.value(), error_list);
        }

        User user = userService.login(loginDto);
        String token = jwtTokenProvider.createToken(loginDto.getEmail(), loginDto.getPw());
        return new ResponseDto(token);
    }

//    @GetMapping("/user")
//    @ResponseBody
//    public ResponseDto getUser(Principal principal) {
//        User user = userService.getUserByEmail(principal.getName());
//        return new ResponseDto(user);
//    }

}
