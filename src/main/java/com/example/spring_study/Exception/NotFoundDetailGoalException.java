package com.example.spring_study.Exception;

public class NotFoundDetailGoalException extends RuntimeException {
    public NotFoundDetailGoalException(String msg) {
        super(msg);
        System.out.println(String.format("msg : %s", msg));
    }
}
