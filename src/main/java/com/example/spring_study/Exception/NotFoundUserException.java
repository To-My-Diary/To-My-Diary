package com.example.spring_study.Exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(String msg){
        super(msg);
        System.out.println(String.format("msg : %s",msg));
    }
}
