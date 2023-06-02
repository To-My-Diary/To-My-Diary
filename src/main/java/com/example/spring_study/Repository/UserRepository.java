package com.example.spring_study.Repository;

<<<<<<< HEAD
import com.example.spring_study.Entity.Goal;
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

    List<User> findByGender(GenderEnum Type);






=======
import com.example.spring_study.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
>>>>>>> 56a958fd51de5c5bd8df58d1041454306cc991d8
}
