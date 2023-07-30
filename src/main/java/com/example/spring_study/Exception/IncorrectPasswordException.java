package com.example.spring_study.Exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String msg){
        super(msg);
        System.out.println(String.format("msg : %s",msg));
    }
}
