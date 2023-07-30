package com.example.spring_study.Security;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(String.format("%s은(는) 없는 이메일 입니다. 다시 확인해주세요.",username));
                });
        return new CustomUserDetails(user);
    }
}
