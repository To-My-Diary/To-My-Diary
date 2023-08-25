package com.example.spring_study.Exception;

import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseDto<ResponseStatus> BaseExceptionHandle(BaseException exception){
        return new ResponseDto<>(exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseDto<ResponseStatus> ExceptionHandle(Exception exception){
        return new ResponseDto<>(ResponseStatus.UNEXPECTED_ERROR);
    }
}
