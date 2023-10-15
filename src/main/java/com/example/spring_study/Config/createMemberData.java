package com.example.spring_study.Config;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.GenderEnum;
import com.example.spring_study.Service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
public class createMemberData {
  @Bean
  public ApplicationRunner init(UserService userService){
    return e -> {
      IntStream.rangeClosed(1,10).forEach(i -> {
        JoinDto joinDto = new JoinDto(
            String.format("member%d",i),
            "1234",
            "1234",
            String.format("nickname%d",i),
            String.format("010-11%d-1111",i),
            GenderEnum.M
        );

        userService.create(joinDto);
      });
    };
  }
}