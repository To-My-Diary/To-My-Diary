package com.example.spring_study.Exception;

public class SignUpTelException extends RuntimeException{
    public SignUpTelException(String msg){
        System.out.println(String.format("msg : %s",msg));
    }
}
