package com.example.spring_study.Service;

import com.example.spring_study.Dto.JoinDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void create(JoinDto joinDto) {
        System.out.println(joinDto.getPw());
        User user = User.builder()
                .email(joinDto.getEmail())
                .pw(passwordEncoder.encode(joinDto.getPw()))
                .name(joinDto.getName())
                .tel(joinDto.getTel())
                .gender(joinDto.getGender())
                .build();
        userRepository.save(user);
    }
}
