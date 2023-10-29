package com.example.spring_study.Util;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.Exception.BaseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.BindingResult;

public class UserValidation {

    private static boolean isRegexEmail(String target) {
        String regex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    private static boolean isRegexPw(String target) {
        // 최소 8자리에 숫자, 문자, 특수 문자 각각 1개 이상 포함
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    private static boolean isRegexTel(String target) {
        // 01[0,1,6~9] - 3,4자리(0~9) - 4자리(0~9) 형식이며, 하이푼을 포함
        String regex = "^01(?:0|1|[6-9])-[0-9]{3,4}-[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static List<String> getValidationError(BindingResult bindingResult) {
        List<String> error_list = new ArrayList<>();

        if (bindingResult.hasErrors()) {  // Spring Validation을 이용하여 null, empty, 공백 검사
            bindingResult.getFieldErrors().forEach(error -> {
                error_list.add(error.getDefaultMessage());
            });
        }
        return error_list;
    }

    public static void joinValidation(JoinDto joinDto) {
        if (!UserValidation.isRegexEmail(joinDto.getEmail())) {  // 이메일 형식 오류
            throw new BaseException(ResponseStatus.POST_EMAIL_INVALID);
        } else if (!UserValidation.isRegexPw(joinDto.getPw())) {   // 비밀번호 형식 오류
            throw new BaseException(ResponseStatus.POST_PASSSWORD_INVALID);
        } else if (!UserValidation.isRegexTel(joinDto.getTel())) {    // 전화번호 형식 오류
            throw new BaseException(ResponseStatus.POST_TEL_INVALID);
        } else if (!joinDto.getPw().equals(joinDto.getConfirmPw())) {  // 비밀번호 확인 시 불일치 오류
            throw new BaseException(ResponseStatus.POST_PASSWORD_DIFF);
        }
    }
}
