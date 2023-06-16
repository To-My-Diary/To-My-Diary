package com.example.spring_study.Entity;

import com.example.spring_study.Enum.GenderEnum;
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
    @Column(length=50, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pw;
    @Column(nullable = false)
    private String name;
    private String tel;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @Builder.Default  // 빌더 패턴 사용시 초기화 설정을 위한 어노테이션
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Goal> goalList = new ArrayList<>();
    public void addGoalList(Goal goal){
        goal.setUser(this);
        this.goalList.add(goal);
    }
}
