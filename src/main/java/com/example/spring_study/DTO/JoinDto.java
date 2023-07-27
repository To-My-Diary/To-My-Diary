package com.example.spring_study.DTO;

import com.example.spring_study.Enum.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class JoinDto {
    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String pw;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String confirmPw;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "연락처를 입력해주세요.")
    private String tel;

    @NotNull(message = "성별을 선택해주세요.")
    private GenderEnum gender;

}
