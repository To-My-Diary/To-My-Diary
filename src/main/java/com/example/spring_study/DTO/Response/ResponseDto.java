package com.example.spring_study.DTO.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.spring_study.DTO.Response.ResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ResponseDto<T> {
    private final Boolean isSuccess;

    private final String message;

    private final int code;

    private T result;

    // 요청 성공 응답객체
    public ResponseDto(T result){
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }
    // 요청 실패 응답객체
    public ResponseDto(boolean isSuccess, String message, int code, T result){
        this.isSuccess = isSuccess;
        this.message = message;
        this.code = code;
        this.result = result;
    }
    // ResponseStatus 이용한 응답객체
    public ResponseDto(ResponseStatus responseStatus){
        this.isSuccess = responseStatus.isSuccess();
        this.message = responseStatus.getMessage();
        this.code = responseStatus.getCode();
    }

}
