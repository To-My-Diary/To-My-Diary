package com.example.spring_study.DTO.Response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatus {
    //    200 상태코드
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),
    JOIN_SUCCESS(true, HttpStatus.OK.value(), "로그인에 성공하였습니다."),
    //    400 상태코드
    POST_EMAIL_DUPLICATE(false, HttpStatus.BAD_REQUEST.value(), "중복된 이메일입니다."),
    POST_PASSWORD_DIFF(false, HttpStatus.BAD_REQUEST.value(), "비밀번호 확인 값이 다릅니다."),
    POST_TEL_DUPLICATE(false, HttpStatus.BAD_REQUEST.value(), "중복된 전화번호입니다."),
    POST_EMAIL_INVALID(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요"),
    POST_PASSSWORD_INVALID(false, HttpStatus.BAD_REQUEST.value(), "비밀번호 양식을 확인해주세요."),
    POST_TEL_INVALID(false, HttpStatus.BAD_REQUEST.value(), "전화번호 양식을 확인해주세요."),
    POST_EMAIL_INCORRECT(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 이메일입니다."),
    POST_PASSWORD_INCORRECT(false, HttpStatus.BAD_REQUEST.value(), "비밀번호가 틀렸습니다"),
    INVALID_JWT(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 JWT입니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;


    private ResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
