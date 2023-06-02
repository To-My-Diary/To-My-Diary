package com.example.spring_study.Entity;

<<<<<<< HEAD
import com.example.spring_study.Enum.GenderType;
=======
import com.example.spring_study.Enum.GenderEnum;
>>>>>>> 56a958fd51de5c5bd8df58d1041454306cc991d8
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder // 빌더 어노테이션 위치 수정
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD
    @Column(length=50, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pw;
    @Column(nullable = false)
    private String pw_val;
    @Column(nullable = false)
    private String name;
    private int tel;
    @Enumerated(EnumType.STRING)
    private GenderType gender;

=======
    @Column(length = 50, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pw;
    @Column(length=20)
    private String name;
    private int tel;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @Builder.Default  // 빌더 패턴 사용시 초기화 설정을 위한 어노테이션
>>>>>>> 56a958fd51de5c5bd8df58d1041454306cc991d8
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Goal> goalList = new ArrayList<>();
    public void addGoalList(Goal goal){
        goal.setUser(this);
        this.goalList.add(goal);
    }
<<<<<<< HEAD
    @Builder
    public User(String email, String pw, String pw_val, String name, GenderType gender, int tel){
        this.email = email;
        this.pw = pw;
        this.pw_val = pw_val;
        this.name = name;
        this.gender = gender;
        this.tel = tel;
    }
=======
>>>>>>> 56a958fd51de5c5bd8df58d1041454306cc991d8
}
