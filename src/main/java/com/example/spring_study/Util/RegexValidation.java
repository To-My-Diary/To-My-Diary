package com.example.spring_study.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidation {
    public static boolean isRegexEmail(String target) {
        String regex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isRegexPw(String target) {
        // 최소 8자리에 숫자, 문자, 특수 문자 각각 1개 이상 포함
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isRegexTel(String target) {
        // 01[0,1,6~9] - 3,4자리(0~9) - 4자리(0~9) 형식이며, 하이푼을 포함
        String regex = "^01(?:0|1|[6-9])-[0-9]{3,4}-[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}
