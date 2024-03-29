package com.example.spring_study.Exception;

import com.example.spring_study.DTO.Response.ResponseStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Getter
@Setter
public class BaseException extends RuntimeException{
    private ResponseStatus status;

    public BaseException(ResponseStatus status){
        super(status.getMessage());
        this.status = status;
    }
}
