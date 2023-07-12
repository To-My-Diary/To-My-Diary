package com.example.spring_study.Dto;

import com.example.spring_study.Enum.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {
    private String email;
    private String pw;
    private String name;
    private String tel;
    private GenderEnum gender;

}
