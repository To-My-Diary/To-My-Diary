package com.example.spring_study.Service;

import static com.example.spring_study.DTO.Response.ResponseStatus.POST_EMAIL_DUPLICATE;
import static com.example.spring_study.DTO.Response.ResponseStatus.POST_EMAIL_INCORRECT;
import static com.example.spring_study.DTO.Response.ResponseStatus.POST_PASSWORD_INCORRECT;
import static com.example.spring_study.DTO.Response.ResponseStatus.POST_TEL_DUPLICATE;
import static com.example.spring_study.DTO.Response.ResponseStatus.SUCCESS;

import com.example.spring_study.DTO.JoinDto;
import com.example.spring_study.DTO.LoginDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Exception.BaseException;
import com.example.spring_study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ResponseDto create(JoinDto joinDto) {
        if (!userRepository.findByEmail(joinDto.getEmail()).isEmpty()) {
            throw new BaseException(POST_EMAIL_DUPLICATE);
        } else if (!userRepository.findByTel(joinDto.getTel()).isEmpty()) {
            throw new BaseException(POST_TEL_DUPLICATE);
        }
        userRepository.save(dtoToEntity(joinDto));
        return new ResponseDto(SUCCESS);
    }

    public User getUserByEmail(String user_email) {
        return userRepository.findByEmail(user_email).get();
    }

    @Transactional(readOnly = true)
    public User login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                () -> new BaseException(POST_EMAIL_INCORRECT)
        );
        if (!bCryptPasswordEncoder.matches(loginDto.getPw(), user.getPw())) {
            throw new BaseException(POST_PASSWORD_INCORRECT);
        }
        AbstractAuthenticationToken abstractAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPw()
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(abstractAuthenticationToken);
        SecurityContextHolder.setContext(securityContext);

        return user;
    }

    private User dtoToEntity(JoinDto joinDto) {
        return User.builder()
                .email(joinDto.getEmail())
                .pw(bCryptPasswordEncoder.encode(joinDto.getPw()))
                .name(joinDto.getName())
                .tel(joinDto.getTel())
                .gender(joinDto.getGender())
                .role("ROLE_USER")
                .build();
    }
}
