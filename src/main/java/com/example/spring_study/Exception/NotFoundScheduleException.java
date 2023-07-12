package com.example.spring_study.Exception;

public class NotFoundScheduleException extends RuntimeException{
    public NotFoundScheduleException(String msg){
        System.out.println(String.format("msg : %s",msg));
    }
}
