package com.example.spring_study.Exception;

public class SignUpEmailException extends RuntimeException{
    public SignUpEmailException(String msg){
        System.out.println(String.format("msg : %s",msg));
    }
}
