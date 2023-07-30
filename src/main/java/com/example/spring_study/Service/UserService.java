package com.example.spring_study.Service;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.LoginDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Exception.IncorrectPasswordException;
import com.example.spring_study.Exception.NotFoundUserException;
import com.example.spring_study.Exception.SignUpEmailException;
import com.example.spring_study.Exception.SignUpTelException;
import com.example.spring_study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void create(JoinDto joinDto) {
        if( !userRepository.findByEmail(joinDto.getEmail()).isEmpty() ){
            throw new SignUpEmailException("이미 존재하는 Email입니다.");
        }else if( !userRepository.findByTel(joinDto.getTel()).isEmpty()){
            throw new SignUpTelException("이미 존재하는 전화번호입니다.");
        }
        User user = User.builder()
                .email(joinDto.getEmail())
                .pw(passwordEncoder.encode(joinDto.getPw()))
                .name(joinDto.getName())
                .tel(joinDto.getTel())
                .gender(joinDto.getGender())
                .build();
        userRepository.save(user);
    }

    public User getUserByEmail(String user_email){
        return userRepository.findByEmail(user_email).get();
    }

    public User login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                () -> new NotFoundUserException(String.format("%s 이메일은 존재하지 않는 이메일입니다.", loginDto.getEmail()))
        );

        if(!passwordEncoder.matches(loginDto.getPw(), user.getPw())){
            new IncorrectPasswordException("비밀번호가 올바르지 않습니다. 다시 입력해주세요.");
        }
        return user;
    }
}
