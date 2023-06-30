package com.example.spring_study.Repository;

import com.example.spring_study.Entity.User;
import com.example.spring_study.Enum.GenderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);  // name값으로 User 조회

    Optional<User> findByEmail(String email);  // email값으로 User 조회

    Optional<User> findByEmailAndPw(String email, String pw);  // email과 password로 User 조회 (생략 가능)

    List<User> findAll();  // 모든 User 조회 (생략 가능)

    List<User> findByGender(GenderEnum Type);  // Gender값으로 User 조회
}
