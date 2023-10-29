package com.example.spring_study.Controller;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.LoginDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Jwt.JwtTokenProvider;
import com.example.spring_study.Service.UserService;
import com.example.spring_study.Util.UserValidation;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
        // Front에서 header값으로 받을 수 있도록 구현
//        response.setHeader("Authorization", "Bearer " + token);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(86400); // 쿠키 유효 기간을 24시간으로 설정
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    @GetMapping("/user")
    @ResponseBody
    public ResponseDto getUser(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        return new ResponseDto(user);
    }

}
